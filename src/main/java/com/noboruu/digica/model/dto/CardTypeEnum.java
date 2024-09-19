package com.noboruu.digica.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CardTypeEnum {
    DIGI_EGG("Digi-Egg"),
    DIGIMON("Digimon"),
    OPTION("Option"),
    TAMER("Tamer");

    private final String wikiCardType;

    public static CardTypeEnum findByWikiCardType(String wikiCardType) {
        for (CardTypeEnum cardTypeEnum : CardTypeEnum.values()) {
            if (cardTypeEnum.getWikiCardType().equals(wikiCardType)) {
                return cardTypeEnum;
            }
        }
        System.out.println("Unknown CardType '" + wikiCardType + "', defaulting to DIGIMON");
        return CardTypeEnum.DIGIMON;
    }
}