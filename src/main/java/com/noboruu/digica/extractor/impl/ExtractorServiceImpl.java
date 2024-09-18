package com.noboruu.digica.extractor.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.noboruu.digica.extractor.ExtractorService;
import com.noboruu.digica.extractor.external.DigicaWikiConnector;
import com.noboruu.digica.extractor.internal.DigicaWikiExtraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class ExtractorServiceImpl implements ExtractorService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public void extract() {
        DigicaWikiConnector connector = new DigicaWikiConnector();
        ObjectWriter ow = new ObjectMapper().findAndRegisterModules().writer().withDefaultPrettyPrinter();
        String fileName = "digicaCards.json";

        try {
            DigicaWikiExtraction wikiExtraction = connector.getAllCardsFromDigicaWiki();
            String cardSetsJson = ow.writeValueAsString(wikiExtraction);
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(cardSetsJson);
            writer.close();
        } catch (IOException ex) {
            LOGGER.error("An error occurred: \n{}", ex.getMessage());
        }
    }
}
