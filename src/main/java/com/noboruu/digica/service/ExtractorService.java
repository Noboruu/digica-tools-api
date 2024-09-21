package com.noboruu.digica.service;

import com.noboruu.digica.model.dto.DigicaWikiExtraction;

public interface ExtractorService {

    DigicaWikiExtraction extract(boolean skipExtracted);

    DigicaWikiExtraction getExtraction();
}
