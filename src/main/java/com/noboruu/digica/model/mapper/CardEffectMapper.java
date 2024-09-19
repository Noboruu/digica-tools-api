package com.noboruu.digica.model.mapper;


import com.noboruu.digica.model.dto.CardEffectDTO;
import com.noboruu.digica.model.dto.CardEffectType;
import com.noboruu.digica.model.entity.CardEffect;

import java.util.ArrayList;
import java.util.List;

public class CardEffectMapper {

    
    public static CardEffectDTO getCardEffectDTO(CardEffect cardEffect) {
        CardEffectDTO cardEffectDTO = new CardEffectDTO();
        cardEffectDTO.setId(cardEffect.getId());
        cardEffectDTO.setEffectText(cardEffect.getEffectText());
        cardEffectDTO.setEffectType(CardEffectType.valueOf(cardEffect.getEffectType()));
        return cardEffectDTO;
    }

    public static CardEffect getCardEffect(CardEffectDTO cardEffectDTO) {
        CardEffect cardEffect = new CardEffect();
        cardEffect.setId(cardEffectDTO.getId());
        cardEffect.setEffectText(cardEffectDTO.getEffectText());
        cardEffect.setEffectType(cardEffectDTO.getEffectType().name());
        return cardEffect;
    }

    public static List<CardEffectDTO> getCardEffectDTOs(List<CardEffect> cardEffects) {
        List<CardEffectDTO> cardEffectDTOs = new ArrayList<>();
        for (CardEffect cardEffect : cardEffects) {
            cardEffectDTOs.add(getCardEffectDTO(cardEffect));
        }
        return cardEffectDTOs;
    }

    public static List<CardEffect> getCardEffects(List<CardEffectDTO> cardEffectDTOs) {
        List<CardEffect> cardEffects = new ArrayList<>();
        for (CardEffectDTO cardEffectDTO : cardEffectDTOs) {
            cardEffects.add(getCardEffect(cardEffectDTO));
        }
        return cardEffects;
    }
}
