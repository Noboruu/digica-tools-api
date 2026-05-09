package com.noboruu.digica.external;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noboruu.digica.model.dto.*;
import com.noboruu.digica.utils.UriUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DigicaWikiApiConnector {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final String DIGICA_WIKI_API_URL = "https://digimoncardgame.fandom.com/api.php";
    private final Pattern REGEX_CARD_NAME_MATCHER = Pattern.compile("(.+)\\s\\((.+)\\)");
    private final String DIGICA_WIKI_SECURITY_EFFECT_TEXT = "Security Effect";
    private final String DIGICA_WIKI_CARD_EFFECT_TEXT = "Card Effect(s)";
    private final String DIGICA_WIKI_INHERITED_EFFECT_TEXT = "Inherited Effect";
    private final String DIGICA_WIKI_ACE_EFFECT_TEXT = "Ace";
    //temporary workaround because P-226 is NOT the last card but its also a non-existant card currently.
    private final List<String> PROMO_CARDS_TO_SKIP = Arrays.asList("P-226", "P-239");

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final DigicaMeta digicaMeta = new DigicaMeta();

    public DigicaWikiApiConnector() {
        this.webClient = WebClient.builder()
                .baseUrl(DIGICA_WIKI_API_URL)
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024)) // 10MB
                .build();
        this.objectMapper = new ObjectMapper();
    }


    private String getResponseFromApi(String pageTitle) throws IOException {
        LOGGER.info("Fetching page via API: {}", pageTitle);

        java.util.Map<String, String> queryParams = new java.util.LinkedHashMap<>();
        queryParams.put("action", "parse");
        queryParams.put("page", pageTitle.replaceAll(":", "%3A"));
        queryParams.put("format", "json");
        queryParams.put("prop", "text");
        queryParams.put("formatversion", "2");

        String response = webClient.get()
                .uri(UriUtils.createUriWithoutEncoding(DIGICA_WIKI_API_URL, queryParams))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        if (response == null) {
            throw new IOException("Empty response from API for page: " + pageTitle);
        }

        return response;
    }

    private Document parseApiResponse(String json) throws IOException {
        JsonNode rootNode = objectMapper.readTree(json);
        
        // Fandom API response for action=parse looks like: {"parse":{"title":"...","pageid":...,"text":"<div class=\"mw-parser-output\">...</div>"}}
        // Note: With formatversion=2, it might be more flat.
        
        JsonNode parseNode = rootNode.get("parse");
        if (parseNode == null || !parseNode.has("text")) {
             return Jsoup.parse("");
        }
        
        String html = parseNode.get("text").asText();
        return Jsoup.parse(html);
    }

    public DigicaWikiExtraction extractFromWiki(List<String> setsToSkip, List<String> promosToSkip, List<String> lmCardsToSkip, boolean cardArtFromDigiprint) throws IOException {
        List<CardSetDTO> cardSets = new ArrayList<>();

        for (DigicaSetsEnum set : DigicaSetsEnum.values()) {
            if (setsToSkip.contains(set.getCode())) {
                continue;
            }

            LOGGER.info("Getting cards for set: " + set.getCode());
            String pageTitle = set.getPath();
            Document doc = parseApiResponse(getResponseFromApi(pageTitle));
            List<String> cardPaths = getAllCardsPathsFromDoc(doc);

            List<CardDTO> cards = new ArrayList<>();
            try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
                for (String cardPath : cardPaths) {
                    if (!cardPath.contains(set.getCode())) {
                        continue;
                    }
                    executor.submit(() -> {
                        try {
                            cards.add(getCardForPath(cardPath, cardArtFromDigiprint));
                        } catch (IOException e) {
                            LOGGER.error("Error fetching card: " + cardPath, e);
                        }
                    });
                }
            }

            CardSetDTO cardSet = getCardSetFromList(cardSets, set.getCode());

            if (!Objects.isNull(cardSet)) {
                cardSet.getCards().addAll(cards);
            } else {
                cardSet = new CardSetDTO();
                cardSet.setCode(set.getCode());
                cardSet.setCards(cards);
                cardSets.add(cardSet);
            }
        }

        cardSets.add(getPromoCardsFromDigicaWiki(promosToSkip, cardArtFromDigiprint));

        DigicaWikiExtraction extraction = new DigicaWikiExtraction();
        extraction.setExtractionDate(LocalDateTime.now());
        extraction.setCardSets(cardSets);

        return extraction;
    }

    private CardSetDTO getCardSetFromList(List<CardSetDTO> cardSets, String setToGet) {
        for (CardSetDTO cardSet : cardSets) {
            if (cardSet.getCode().equals(setToGet)) {
                return cardSet;
            }
        }
        return null;
    }

    private CardDTO getCardForPath(String path, boolean cardArtFromDigiprint) throws IOException {
        String pageTitle = path.replace("/wiki/", "");
        Document doc = parseApiResponse(getResponseFromApi(pageTitle));

        return getCardForPath(doc, cardArtFromDigiprint);
    }

    private CardDTO getCardForPath(Document doc, boolean cardArtFromDigiprint) {
        CardDTO card = new CardDTO();
        getCardNameAndCode(doc, card);
        getCardType(doc, card);
        getCardArtUrl(doc, card, cardArtFromDigiprint);
        getCardEffects(doc, card);
        return card;
    }

    private List<String> getAllCardsPathsFromDoc(Document doc) {
        List<String> cardPaths = new ArrayList<>();

        List<Element> cardTables = doc.select("table.cardlist");

        for (Element cardTable : cardTables) {
            Element cardTableTbody = cardTable.select("tbody").first();
            if (cardTableTbody == null) continue;
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

    private CardSetDTO getPromoCardsFromDigicaWiki(List<String> promosToSkip, boolean cardArtFromDigiprint) throws IOException {
        LOGGER.info("Getting promo cards from Digica Wiki via API");
        List<CardDTO> cards = new ArrayList<>();

        for (int promoNumber = 1; promoNumber < 1000; promoNumber++) {
            String promoCode = buildPromoCode(promoNumber);
            if(PROMO_CARDS_TO_SKIP.contains(promoCode) || promosToSkip.contains(promoCode)) {
                continue;
            }

            String json = getResponseFromApi(promoCode);
            if (json.contains("\"error\"")) {
                LOGGER.info("Found last promo card at {}!", promoCode);
                break;
            }
            Document doc = parseApiResponse(json);
            cards.add(getCardForPath(doc, cardArtFromDigiprint));
        }

        CardSetDTO cardSet = new CardSetDTO();
        cardSet.setCode("Promo");
        cardSet.setCards(cards);
        return cardSet;
    }
    
    // I should refine getPromoCardsFromDigicaWiki to better match original's behavior but with API.
    // Actually, I'll just use the sequential approach for promo discovery if I want to stop at the first 404.

    private String buildPromoCode(int promoNumber) {
        if (promoNumber < 10) {
            return "P-00" + promoNumber;
        } else if (promoNumber < 100) {
            return "P-0" + promoNumber;
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

            card.setName(m.group(1).replaceAll("< ", ""));
            card.setCode(m.group(2));
        }
    }

    private void getCardType(Document doc, CardDTO card) {
        Element cardTypeElement = doc.select("[title='Card Types']").first();

        if(Objects.isNull(cardTypeElement)) {
            cardTypeElement = doc.select("[title='Digimon ACE']").first();
        }

        if (!Objects.isNull(cardTypeElement)) {
            CardTypeEnum cardType = CardTypeEnum.findByWikiCardType(cardTypeElement.text());
            card.setCardType(cardType);
            return;
        }

        throw new IllegalArgumentException("Invalid card type for card with code " + card.getCode());
    }

    private void getCardArtUrl(Document doc, CardDTO card, boolean cardArtFromDigiprint) {
        if (cardArtFromDigiprint) {
            card.setArtUrl(Digiprintmon.buildDigiprintmonCardArtURL(card.getCode()));
            return;
        }

        if (digicaMeta.isToGetArtFromDigicaMeta(card.getCode())) {
            card.setArtUrl(digicaMeta.getArtUrlFromDigimonMeta(card.getCode()));
            return;
        }

        card.setArtUrl(getCardArtUrlFromWiki(doc));
    }

    private String getCardArtUrlFromWiki(Document doc) {
        Element cardArtElement = doc.select("a.image").first();
        if (!Objects.isNull(cardArtElement)) {
            String url = cardArtElement.attr("href");
            if (!StringUtils.isBlank(url)) {
                Pattern pattern = Pattern.compile("(^https://.+)(/revision/latest.+)");
                Matcher matcher = pattern.matcher(url);
                if (matcher.find()) {
                    String newUrl = matcher.group(1);
                    return newUrl;
                }
            }
        }
        return null;
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
