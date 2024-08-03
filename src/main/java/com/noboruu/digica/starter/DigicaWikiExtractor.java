package com.noboruu.digica.starter;

import com.noboruu.digica.extractor.external.DigicaWikiConnector;
import com.noboruu.digica.extractor.internal.CardSet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DigicaWikiExtractor {

    public static void main(String[] args) {
        DigicaWikiConnector connector = new DigicaWikiConnector();
        List<CardSet> cardSets = new ArrayList<>();
        try {
            cardSets = connector.getAllCardsFromDigicaWiki();
        } catch (IOException ex) {
            System.out.println("An error occurred: \n" + ex.getMessage());
        }
        System.out.println("yay");
    }
}
