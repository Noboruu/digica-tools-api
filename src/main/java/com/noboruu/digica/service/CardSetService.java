package com.noboruu.digica.service;

import com.noboruu.digica.model.dto.CardSetDTO;

import java.util.List;

public interface CardSetService {

    void persist(List<CardSetDTO> cardSets);

    List<CardSetDTO> findAll(boolean loadCards);
}
