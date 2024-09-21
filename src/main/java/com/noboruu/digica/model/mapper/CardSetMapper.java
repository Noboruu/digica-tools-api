package com.noboruu.digica.model.mapper;


import com.noboruu.digica.model.dto.CardSetDTO;
import com.noboruu.digica.model.entity.CardSet;

import java.util.ArrayList;
import java.util.List;

public class CardSetMapper {

    public static CardSetDTO getCardSetDTO(CardSet cardSet, boolean loadCards) {
        CardSetDTO cardSetDTO = new CardSetDTO();
        cardSetDTO.setId(cardSet.getId());
        cardSetDTO.setCode(cardSet.getCode());
        if(loadCards) {
            cardSetDTO.setCards(CardMapper.getCardDTOs(cardSet.getCards()));
        }
        return cardSetDTO;
    }

    public static CardSet getCardSet(CardSetDTO cardSetDTO, boolean loadCards) {
        CardSet cardSet = new CardSet();
        cardSet.setId(cardSetDTO.getId());
        cardSet.setCode(cardSetDTO.getCode());
        if(loadCards) {
            cardSet.setCards(CardMapper.getCards(cardSetDTO.getCards()));
        }
        return cardSet;
    }

    public static List<CardSetDTO> getCardSetDTOs(List<CardSet> cardSets, boolean loadCards) {
        List<CardSetDTO> cardSetDTOs = new ArrayList<>();
        for (CardSet cardSet : cardSets) {
            cardSetDTOs.add(getCardSetDTO(cardSet, loadCards));
        }
        return cardSetDTOs;
    }

    public static List<CardSet> getCardSets(List<CardSetDTO> cardSetDTOSet, boolean loadCards) {
        List<CardSet> cardSets = new ArrayList<>();
        for (CardSetDTO cardSetDTO : cardSetDTOSet) {
            cardSets.add(getCardSet(cardSetDTO, loadCards));
        }
        return cardSets;
    }
}
