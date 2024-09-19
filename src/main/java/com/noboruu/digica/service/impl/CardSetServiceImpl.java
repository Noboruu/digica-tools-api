package com.noboruu.digica.service.impl;

import com.noboruu.digica.model.dto.CardSetDTO;
import com.noboruu.digica.model.entity.CardSet;
import com.noboruu.digica.model.mapper.CardSetMapper;
import com.noboruu.digica.repository.CardSetRepository;
import com.noboruu.digica.service.CardSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardSetServiceImpl implements CardSetService {

    @Autowired
    private CardSetRepository cardSetRepository;

    @Override
    public void persist(List<CardSetDTO> cardSetDTOs) {
        List<CardSet> cardSets = CardSetMapper.getCardSets(cardSetDTOs);
        cardSetRepository.saveAll(cardSets);
    }
}
