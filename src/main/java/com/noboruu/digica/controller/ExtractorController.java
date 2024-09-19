package com.noboruu.digica.controller;

import com.noboruu.digica.model.dto.DigicaWikiExtraction;
import com.noboruu.digica.service.ExtractorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/extractor")
public class ExtractorController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ExtractorService extractorService;

    @GetMapping("/extract")
    public DigicaWikiExtraction extract() {
        LOGGER.info("Request to extract digica cards received!");
        DigicaWikiExtraction extraction = extractorService.extract();
        LOGGER.info("Done extracting digica cards!");
        return extraction;
    }
}
