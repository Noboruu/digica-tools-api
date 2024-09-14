package com.noboruu.digica.extractor.external;


import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// This classes only serves the purpose of getting cards from digimonMeta while there haven't been english version announced
// As soon as the english versions get announced, they'll be added to the wiki and we can get them from there as normal
// This class will be removed as soon as Digica Global, and Digica JP are releasing at the same time, aka, in early 2025
@NoArgsConstructor
public class DigicaMeta {
    private final String DIGICA_META_BASE_URL = "https://digimonmeta.com/wp-content/gallery/";
    private final String CART_ART_FILE_EXTENSION = ".jpg";
    private final List<String> CARD_META_SETS = Arrays.asList("ST18", "ST19", "BT18", "EX8"); // remove sets from here as they get released in global
    private final String PROMO_EXTRACTED_CARD_SET = "p";
    private final String DIGICA_META_PROMO_CARD_SET = "promotion-01";
    private final List<String> CARD_SET_TYPE_FOR_FIXING = Arrays.asList("BT", "EX");
    private int LAST_WIKI_EN_PROMO = 151;
    private String LM_SET = "LM";
    private int LAST_WIKI_LM = 20;

    public boolean isToGetArtFromDigicaMeta(String cardCode) {
        String cardSet = getSetFromCardCode(cardCode);
        int cardNumber = Integer.parseInt(getCardNumberFromCardCode(cardCode));
        if(PROMO_EXTRACTED_CARD_SET.equalsIgnoreCase(cardSet) && cardNumber > LAST_WIKI_EN_PROMO) {
            return !(cardNumber >= 159); //digimon meta doesnt have promo 159, and its better to have jp art than no art at all
        }

        if(LM_SET.equalsIgnoreCase(cardSet) && cardNumber > LAST_WIKI_LM ) {
            return true;
        }

        return CARD_META_SETS.contains(cardSet);
    }

    public String getArtUrlFromDigimonMeta(String cardCode) {
        return (DIGICA_META_BASE_URL + getDigicaMetaCardSetFromCardCode(cardCode).toLowerCase() + "/" + cardCode + CART_ART_FILE_EXTENSION);
    }

    private String getSetFromCardCode(String cardCode) {
        Pattern p = Pattern.compile("(.+)-(.+)");
        Matcher m = p.matcher(cardCode);
        if(!m.find()) {
            throw new IllegalArgumentException("Invalid card: " + cardCode);
        }

        return m.group(1);
    }

    private String getCardNumberFromCardCode(String cardCode) {
        Pattern p = Pattern.compile("(.+)-(.+)");
        Matcher m = p.matcher(cardCode);
        if(!m.find()) {
            throw new IllegalArgumentException("Invalid card: " + cardCode);
        }

        return m.group(2);
    }

    private String getDigicaMetaCardSetFromCardCode(String cardCode) {
        String cardSet = getSetFromCardCode(cardCode);

        if(PROMO_EXTRACTED_CARD_SET.equalsIgnoreCase(cardSet)) {
            return DIGICA_META_PROMO_CARD_SET;
        }

        if(LM_SET.equalsIgnoreCase(cardSet)) {
            return LM_SET;
        }

        Pattern p = Pattern.compile("(\\D+)(\\d+)");
        Matcher m = p.matcher(cardSet);
        if (!m.find()) {
            throw new IllegalArgumentException("Invalid set: " + cardSet);
        }

        if(CARD_SET_TYPE_FOR_FIXING.contains(m.group(1)) && Integer.parseInt(m.group(2)) < 10) {
            return m.group(1) + "0" + m.group(2);
        }

        return cardSet;
    }
}
