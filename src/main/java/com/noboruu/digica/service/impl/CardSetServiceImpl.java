package com.noboruu.digica.service.impl;

import com.noboruu.digica.model.dto.CardSetDTO;
import com.noboruu.digica.model.entity.Card;
import com.noboruu.digica.model.entity.CardEffect;
import com.noboruu.digica.model.entity.CardSet;
import com.noboruu.digica.model.mapper.CardSetMapper;
import com.noboruu.digica.repository.CardSetRepository;
import com.noboruu.digica.service.CardSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CardSetServiceImpl implements CardSetService {

    @Autowired
    private CardSetRepository repository;

    @Transactional
    @Override
    public void persist(List<CardSetDTO> cardSetDTOs) {
        List<CardSet> cardSets = CardSetMapper.getCardSets(cardSetDTOs, true);
        for(CardSet cardSet : cardSets) {
            for(Card card : cardSet.getCards()) {
                card.setCardSet(cardSet);
                for(CardEffect cardEffect : card.getCardEffects()) {
                    cardEffect.setCard(card);
                }
            }
        }
        repository.saveAll(cardSets);

    }

    @Transactional
    @Override
    public List<CardSetDTO> findAll(boolean loadCards) {
        return CardSetMapper.getCardSetDTOs(repository.findAllByOrderByIdAsc(), loadCards);
    }
}
