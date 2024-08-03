package com.noboruu.digica.extractor.internal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class CardSet {
    private String code;
    private List<Card> cards;
}
