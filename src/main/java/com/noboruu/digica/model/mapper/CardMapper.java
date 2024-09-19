package com.noboruu.digica.model.mapper;

import com.noboruu.digica.model.dto.CardDTO;
import com.noboruu.digica.model.dto.CardTypeEnum;
import com.noboruu.digica.model.entity.Card;

import java.util.ArrayList;
import java.util.List;

public class CardMapper {

    public static CardDTO getCardDTO(Card card) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setId(card.getId());
        cardDTO.setCode(card.getCode());
        cardDTO.setName(card.getName());
        cardDTO.setCardType(CardTypeEnum.valueOf(card.getCardType()));
        cardDTO.setArtUrl(card.getArtUrl());
        cardDTO.setCardEffects(CardEffectMapper.getCardEffectDTOs(card.getCardEffects()));
        return cardDTO;
    }

    public static Card getCard(CardDTO cardDTO) {
        Card card = new Card();
        card.setId(cardDTO.getId());
        card.setCode(cardDTO.getCode());
        card.setName(cardDTO.getName());
        card.setCardType(cardDTO.getCardType().name());
        card.setArtUrl(cardDTO.getArtUrl());
        card.setCardEffects(CardEffectMapper.getCardEffects(cardDTO.getCardEffects()));
        return card;
    }

    public static List<CardDTO> getCardDTOs(List<Card> cards) {
        List<CardDTO> cardDTOSet = new ArrayList<>();
        for (Card card : cards) {
            cardDTOSet.add(getCardDTO(card));
        }
        return cardDTOSet;
    }

    public static List<Card> getCards(List<CardDTO> cardDTOs) {
        List<Card> cardSet = new ArrayList<>();
        for (CardDTO cardDTO : cardDTOs) {
            cardSet.add(getCard(cardDTO));
        }
        return cardSet;
    }
}
