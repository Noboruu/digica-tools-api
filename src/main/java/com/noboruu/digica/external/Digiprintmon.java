package com.noboruu.digica.external;

public class Digiprintmon {
    private final static String DIGIPRINT_BASE_URL = "https://www.digiprintmon.com/img/";
    private final static String DIGIPRINT_FILE_EXTENSION = ".jpg";

    public static String buildDigiprintmonCardArtURL(String setAndCardNumb) {
        return DIGIPRINT_BASE_URL + setAndCardNumb + DIGIPRINT_FILE_EXTENSION;
    }
}
