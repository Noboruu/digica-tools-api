package com.noboruu.digica.service.impl;

import com.noboruu.digica.external.DigicaWikiConnector;
import com.noboruu.digica.model.dto.CardDTO;
import com.noboruu.digica.model.dto.CardSetDTO;
import com.noboruu.digica.model.dto.DigicaWikiExtraction;
import com.noboruu.digica.service.CardSetService;
import com.noboruu.digica.service.ExtractorService;
import com.noboruu.digica.utils.CardNaturalOrderComparator;
import com.noboruu.digica.utils.CardSetNaturalOrderComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExtractorServiceImpl implements ExtractorService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CardSetService cardSetService;

    @Override
    public DigicaWikiExtraction extract(boolean skipExtracted) {
        DigicaWikiConnector connector = new DigicaWikiConnector();
        DigicaWikiExtraction extraction = new DigicaWikiExtraction();
        List<CardSetDTO> extractedCardSets = cardSetService.findAll(true);
        List<String> setsToSkip = new ArrayList<>();
        List<String> promoCardsToSkip = new ArrayList<>();
        List<String> lmCardsToSkip = new ArrayList<>();

        setCardsToSkip(setsToSkip, promoCardsToSkip, lmCardsToSkip, extractedCardSets, skipExtracted);

        try {
            extraction = connector.extractFromWiki(setsToSkip, promoCardsToSkip, lmCardsToSkip);
            addIdsToCardSetsForUpdate(extraction, extractedCardSets, skipExtracted);
            sortExtraction(extraction); //sort before persisting to database
            cardSetService.persist(extraction.getCardSets());
            if(skipExtracted) {
                extraction.getCardSets().addAll(extractedCardSets);
                sortExtraction(extraction); //sort before returning http response
            }
        } catch (IOException ex) {
            LOGGER.error("An error occurred: \n{}", ex.getMessage());
        }

        return extraction;
    }

    @Override
    public DigicaWikiExtraction getExtraction() {
        DigicaWikiExtraction extraction = new DigicaWikiExtraction();
        extraction.setCardSets(cardSetService.findAll(true));
        extraction.setExtractionDate(LocalDateTime.now());

        return extraction;
    }

    private void sortExtraction(DigicaWikiExtraction extraction) {
        LOGGER.info("Sorting extraction");
        extraction.getCardSets().sort(new CardSetNaturalOrderComparator());

        for (CardSetDTO cardSet : extraction.getCardSets()) {
            cardSet.getCards().sort(new CardNaturalOrderComparator());
        }
        LOGGER.info("Finished sorting extraction!");
    }

    private void addIdsToCardSetsForUpdate(DigicaWikiExtraction extraction, List<CardSetDTO> oldCardSets, boolean skipExtracted) {
        // Don't do anything if current extraction is skipping already extracted sets
        // If it wasn't skipped, then we potentially have to update old values, so we need their IDs
        if(!skipExtracted) {
            for(CardSetDTO newCardSet : extraction.getCardSets()) {
                for(CardSetDTO oldCardSet : oldCardSets) {
                    if(oldCardSet.getCode().equals(newCardSet.getCode())) {
                        newCardSet.setId(oldCardSet.getId());
                        addIdsToCardsFromSetForUpdate(newCardSet.getCards(), oldCardSet.getCards());
                        break;
                    }
                }
            }
        }
    }

    private void addIdsToCardsFromSetForUpdate(List<CardDTO> newCards, List<CardDTO> oldCards) {
        for(CardDTO newCard : newCards) {
            for(CardDTO oldCard : oldCards) {
                if(oldCard.getCode().equals(newCard.getCode())) {
                    newCard.setId(oldCard.getId());
                }
            }
        }
    }

    private void setCardsToSkip(List<String> setsToSkip, List<String> promoCardsToSkip, List<String> lmCardsToSkip, List<CardSetDTO> extractedCardSets, boolean skipExtracted) {
        if (skipExtracted) {
            for (CardSetDTO cardSetDTO : extractedCardSets) {
                if(cardSetDTO.getCode().equals("LM")) {
                    for(CardDTO cardDTO : cardSetDTO.getCards()) {
                        lmCardsToSkip.add(cardDTO.getCode());
                    }
                    continue;
                }

                if(cardSetDTO.getCode().equals("Promo")) {
                    for(CardDTO cardDTO : cardSetDTO.getCards()) {
                        promoCardsToSkip.add(cardDTO.getCode());
                    }
                    continue;
                }

                setsToSkip.add(cardSetDTO.getCode());
            }
        }
    }

}
