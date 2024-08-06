package com.noboruu.digica.starter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.noboruu.digica.extractor.external.DigicaWikiConnector;
import com.noboruu.digica.extractor.internal.CardSet;
import com.noboruu.digica.extractor.internal.DigicaWikiExtraction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DigicaWikiExtractor {

    public static void main(String[] args) {
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
            System.out.println("An error occurred: \n" + ex.getMessage());
        }
    }
}
