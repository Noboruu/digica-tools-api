package com.noboruu.digica.extractor.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardEffect {
    private CardEffectType effectType;
    private String effectText;
}
