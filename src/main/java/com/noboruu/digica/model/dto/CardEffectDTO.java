package com.noboruu.digica.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardEffectDTO {
    @JsonIgnore
    private Long id;
    private CardEffectType effectType;
    private String effectText;

    public CardEffectDTO(CardEffectType effectType, String effectText) {
        this.effectType = effectType;
        this.effectText = effectText;
    }
}
