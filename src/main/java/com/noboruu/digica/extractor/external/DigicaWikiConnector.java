package com.noboruu.digica.extractor.external;

import com.noboruu.digica.extractor.internal.Card;
import com.noboruu.digica.extractor.internal.CardSet;
import com.noboruu.digica.extractor.internal.CardTypeEnum;
import com.noboruu.digica.extractor.internal.DigicaWikiExtraction;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DigicaWikiConnector {
    private final String DIGICA_WIKI_BASE_URL = "https://digimoncardgame.fandom.com";
    private final String DIGICA_WIKI_PROMOS_PATH = "/wiki/P-";
    private final Pattern REGEX_CARD_NAME_MATCHER = Pattern.compile("(.+)\\s\\((.+)\\)");

    private final DigicaMeta digicaMeta = new DigicaMeta();

    public DigicaWikiExtraction getAllCardsFromDigicaWiki() throws IOException {
        List<CardSet> cardSets = new ArrayList<>();

        for (DigicaSetsEnum set : DigicaSetsEnum.values()) {
            System.out.println("Getting cards for set: " + set.getCode());
            String url = DIGICA_WIKI_BASE_URL + set.getPath();
            List<String> cardPaths = getAllCardsPathsForUrl(url);

            List<Card> cards = new ArrayList<>();
            for (String cardPath : cardPaths) {
                if (!cardPath.contains(set.getCode())) {
                    continue;
                }
                cards.add(getCardForPath(cardPath));
            }
            CardSet cardSet = new CardSet();
            cardSet.setCode(set.getCode());
            cardSet.setCards(cards);
            cardSets.add(cardSet);
        }
        cardSets.add(getPromoCardsFromDigicaWiki());
        return new DigicaWikiExtraction(LocalDateTime.now(), cardSets);
    }

    private Card getCardForPath(String path) throws IOException {
        String url = DIGICA_WIKI_BASE_URL + path;
        Document doc = Jsoup.connect(url).get();

        return getCardForPath(doc);
    }

    private Card getCardForPath(Document doc)  {
        Card card = new Card();

        Element cardNameElement = doc.getElementsByClass("mw-headline").first();
        if (!Objects.isNull(cardNameElement)) {
            String normalized = Normalizer.normalize(cardNameElement.text(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
            Matcher m = REGEX_CARD_NAME_MATCHER.matcher(normalized);
            if (!m.find()) {
                throw new IllegalArgumentException("Invalid card name: " + normalized);
            }
            card.setName(m.group(1));
            card.setCode(m.group(2));
        }

        Element cardTypeElement = doc.select("[title='Card Types']").first();
        if (!Objects.isNull(cardTypeElement)) {
            CardTypeEnum cardType = CardTypeEnum.findByWikiCardType(cardTypeElement.text());
            card.setCardType(cardType);
        }

        if(digicaMeta.isToGetArtFromDigicaMeta(card.getCode())) {
            card.setArtUrl(digicaMeta.getArtUrlFromDigimonMeta(card.getCode()));
        } else {
            Element cardArtElement = doc.select("a.image").first();
            if (!Objects.isNull(cardArtElement)) {
                card.setArtUrl(cardArtElement.attr("href"));
            }
        }

        return card;
    }

    private List<String> getAllCardsPathsForUrl(String url) throws IOException {
        List<String> cardPaths = new ArrayList<>();

        Document doc = Jsoup.connect(url).get();
        Element cardTables = doc.select("table.cardlist").first(); //todo null check
        Element cardTableTbody = cardTables.select("tbody").first(); //todo null check
        Elements cardTableLinks = cardTableTbody.select("a");

        for (Element aElement : cardTableLinks) {
            String path = aElement.attr("href");
            if (!StringUtils.isBlank(path) && !path.contains("Card_Types") && !cardPaths.contains(path)) {
                cardPaths.add(path);
            }
        }
        return cardPaths;
    }

    private CardSet getPromoCardsFromDigicaWiki() throws IOException {
        System.out.println("Getting promo cards from Digica Wiki");
        List<Card> cards = new ArrayList<>();

        try {
            for (int promoNumber = 1; promoNumber < 1000; promoNumber++) {
                String url = buildPromoUrl(promoNumber);
                Document doc = Jsoup.connect(url).get();
                cards.add(getCardForPath(doc));
            }
        } catch (HttpStatusException e) {
            System.out.println("Found last promo card!");
        }

        CardSet cardSet = new CardSet();
        cardSet.setCode("Promo");
        cardSet.setCards(cards);
        return cardSet;
    }

    private String buildPromoUrl(int promoNumber) {
        String basePromoUrl = DIGICA_WIKI_BASE_URL + DIGICA_WIKI_PROMOS_PATH;
        if (promoNumber < 10) {
            return basePromoUrl + "00" + promoNumber;
        } else if (promoNumber < 100) {
            return basePromoUrl + "0" + promoNumber;
        }

        return basePromoUrl + promoNumber;
    }


}
