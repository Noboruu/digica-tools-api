package com.noboruu.digica.external;

import com.noboruu.digica.model.dto.*;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DigicaWikiConnector {
    // TODO: Refactor this whole class. It was done on the early stages of this project, before it was a RESTful spring API

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final String DIGICA_WIKI_BASE_URL = "https://digimoncardgame.fandom.com";
    private final String DIGICA_WIKI_PROMOS_PATH = DIGICA_WIKI_BASE_URL + "/wiki/";
    private final Pattern REGEX_CARD_NAME_MATCHER = Pattern.compile("(.+)\\s\\((.+)\\)");
    private final String DIGICA_WIKI_SECURITY_EFFECT_TEXT = "Security Effect";
    private final String DIGICA_WIKI_CARD_EFFECT_TEXT = "Card Effect(s)";
    private final String DIGICA_WIKI_INHERITED_EFFECT_TEXT = "Inherited Effect";
    private final String DIGICA_WIKI_ACE_EFFECT_TEXT = "Ace";

    private final DigicaMeta digicaMeta = new DigicaMeta();

    public DigicaWikiExtraction extractFromWiki(List<String> setsToSkip, List<String> promosToSkip, List<String> lmCardsToSkip) throws IOException {
        List<CardSetDTO> cardSets = new ArrayList<>();

        for (DigicaSetsEnum set : DigicaSetsEnum.values()) {
            if(setsToSkip.contains(set.getCode())) {
                continue;
            }

            LOGGER.info("Getting cards for set: " + set.getCode());
            String url = DIGICA_WIKI_BASE_URL + set.getPath();
            List<String> cardPaths = getAllCardsPathsForUrl(url);

            List<CardDTO> cards = new ArrayList<>();
            try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
                for (String cardPath : cardPaths) {
                    if (!cardPath.contains(set.getCode())) {
                        continue;
                    }
                    executor.submit(() -> cards.add(getCardForPath(cardPath)));
                }
            }

            CardSetDTO cardSet = getCardSetFromList(cardSets, set.getCode());

            if(!Objects.isNull(cardSet)) {
                cardSet.getCards().addAll(cards);
            } else {
                cardSet = new CardSetDTO();
                cardSet.setCode(set.getCode());
                cardSet.setCards(cards);
                cardSets.add(cardSet);
            }

            // remove duplicates from set
            cardSet.setCards(getCardListWithoutDuplicates(cardSet.getCards()));
        }


        cardSets.add(getPromoCardsFromDigicaWiki(promosToSkip));
        return new DigicaWikiExtraction(LocalDateTime.now(), cardSets);
    }

    private List<CardDTO> getCardListWithoutDuplicates(List<CardDTO> cards) {
        List<CardDTO> newCards = new ArrayList<>();
        List<String> extractedCardCodes = new ArrayList<>(); //easier to manage the extraction this way

        for (CardDTO card : cards) {
            if(!extractedCardCodes.contains(card.getCode())) {
                newCards.add(card);
                extractedCardCodes.add(card.getCode());
            }
        }

        return newCards;
    }

    private CardSetDTO getCardSetFromList(List<CardSetDTO> cardSets, String setToGet) {
        for(CardSetDTO cardSet : cardSets) {
            if(cardSet.getCode().equals(setToGet)) {
                return cardSet;
            }
        }
        return null;
    }

    private CardDTO getCardForPath(String path) throws IOException {
        String url = DIGICA_WIKI_BASE_URL + path;
        Document doc = Jsoup.connect(url).get();

        return getCardForPath(doc);
    }

    private CardDTO getCardForPath(Document doc) {
        CardDTO card = new CardDTO();
        getCardNameAndCode(doc, card);
        getCardType(doc, card);
        getCardArtUrl(doc, card);
        getCardEffects(doc, card);
        return card;
    }

    private List<String> getAllCardsPathsForUrl(String url) throws IOException {
        List<String> cardPaths = new ArrayList<>();

        Document doc = Jsoup.connect(url).get();
        List<Element> cardTables = doc.select("table.cardlist");

        for(Element cardTable : cardTables) {
            Element cardTableTbody = cardTable.select("tbody").first(); //todo null check
            Elements cardTableLinks = cardTableTbody.select("a");

            for (Element aElement : cardTableLinks) {
                String path = aElement.attr("href");
                if (!StringUtils.isBlank(path) && !path.contains("Card_Types") && !cardPaths.contains(path)) {
                    cardPaths.add(path);
                }
            }
        }

        return cardPaths;
    }

    private CardSetDTO getPromoCardsFromDigicaWiki(List<String> promosToSkip) throws IOException {
        LOGGER.info("Getting promo cards from Digica Wiki");
        List<CardDTO> cards = new ArrayList<>();

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int promoNumber = 1; promoNumber < 1000; promoNumber++) {
                String promoCode = buildPromoCode(promoNumber);
                if(promosToSkip.contains(promoCode)) {
                    continue;
                }

                Document doc = Jsoup.connect(DIGICA_WIKI_PROMOS_PATH + promoCode).get();
                executor.submit(() -> cards.add(getCardForPath(doc)));
            }
        } catch (HttpStatusException e) {
            LOGGER.info("Found last promo card!");
        }

        CardSetDTO cardSet = new CardSetDTO();
        cardSet.setCode("Promo");
        cardSet.setCards(cards);
        return cardSet;
    }

    private String buildPromoCode(int promoNumber) {
        if (promoNumber < 10) {
            return "P-00" + promoNumber;
        } else if (promoNumber < 100) {
            return  "P-0" + promoNumber;
        }
        return "P-" + promoNumber;
    }

    private void getCardNameAndCode(Document doc, CardDTO card) {
        Element cardNameElement = doc.getElementsByClass("mw-headline").first();
        if (!Objects.isNull(cardNameElement)) {
            Matcher m = REGEX_CARD_NAME_MATCHER.matcher(cardNameElement.text());
            if (!m.find()) {
                throw new IllegalArgumentException("Invalid card name: " + cardNameElement);
            }
            card.setName(m.group(1));
            card.setCode(m.group(2));
        }
    }

    private void getCardType(Document doc, CardDTO card) {
        Element cardTypeElement = doc.select("[title='Card Types']").first();
        if (!Objects.isNull(cardTypeElement)) {
            CardTypeEnum cardType = CardTypeEnum.findByWikiCardType(cardTypeElement.text());
            card.setCardType(cardType);
        }
    }

    private void getCardArtUrl(Document doc, CardDTO card) {
        if (digicaMeta.isToGetArtFromDigicaMeta(card.getCode())) {
            card.setArtUrl(digicaMeta.getArtUrlFromDigimonMeta(card.getCode()));
        } else {
            Element cardArtElement = doc.select("a.image").first();
            if (!Objects.isNull(cardArtElement)) {
                card.setArtUrl(cardArtElement.attr("href"));
            }
        }
    }

    private void getCardEffects(Document doc, CardDTO card) {
        Elements effectTables = doc.select("table.effect");

        for (Element effectTable : effectTables) {
            Element th = effectTable.select("th").first();
            Element td = effectTable.select("td").first();
            if (!Objects.isNull(th) && !Objects.isNull(td) && !StringUtils.isBlank(td.text())) {
                if (DIGICA_WIKI_SECURITY_EFFECT_TEXT.equalsIgnoreCase(th.text())) {
                    card.getCardEffects().add(new CardEffectDTO(CardEffectType.SECURITY, td.text()));
                } else if (DIGICA_WIKI_CARD_EFFECT_TEXT.equalsIgnoreCase(th.text())) {
                    card.getCardEffects().add(new CardEffectDTO(CardEffectType.CARD, td.text()));
                } else if (DIGICA_WIKI_INHERITED_EFFECT_TEXT.equalsIgnoreCase(th.text())) {
                    card.getCardEffects().add(new CardEffectDTO(CardEffectType.INHERITED, td.text()));
                } else if (DIGICA_WIKI_ACE_EFFECT_TEXT.equalsIgnoreCase(th.text())) {
                    card.getCardEffects().add(new CardEffectDTO(CardEffectType.ACE, td.text()));
                }
            }
        }
    }

}
