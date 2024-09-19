package com.noboruu.digica.service.impl;

import com.noboruu.digica.external.DigicaWikiConnector;
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

@Service
public class ExtractorServiceImpl implements ExtractorService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CardSetService cardSetService;

    @Override
    public DigicaWikiExtraction extract() {
        DigicaWikiConnector connector = new DigicaWikiConnector();
        DigicaWikiExtraction extraction = new DigicaWikiExtraction();
        try {
            extraction = connector.getAllCardsFromDigicaWiki();
            sortExtraction(extraction);
            cardSetService.persist(extraction.getCardSets());
        } catch (IOException ex) {
            LOGGER.error("An error occurred: \n{}", ex.getMessage());
        }
        return extraction;
    }

    private void sortExtraction(DigicaWikiExtraction extraction) {
        LOGGER.info("Sorting extraction");
        extraction.getCardSets().sort(new CardSetNaturalOrderComparator());

        for(CardSetDTO cardSet : extraction.getCardSets()) {
            cardSet.getCards().sort(new CardNaturalOrderComparator());
        }
        LOGGER.info("Finished sorting extraction!");
    }

}
