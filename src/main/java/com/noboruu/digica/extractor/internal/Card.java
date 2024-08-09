package com.noboruu.digica.extractor.internal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Card {
    private String code;
    private String name;
    private String artUrl;
    private CardTypeEnum cardType;
}