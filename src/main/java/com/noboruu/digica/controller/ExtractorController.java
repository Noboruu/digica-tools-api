package com.noboruu.digica.controller;

import com.noboruu.digica.model.dto.DigicaWikiExtraction;
import com.noboruu.digica.service.ExtractorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/extractor")
public class ExtractorController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ExtractorService service;

    @GetMapping("/extract")
    public @ResponseBody DigicaWikiExtraction extract(@RequestParam(required = false, defaultValue = "false") boolean skipExtracted) {
        LOGGER.info("Request to extract digica cards received!");
        DigicaWikiExtraction extraction = service.extract(skipExtracted);
        LOGGER.info("Done extracting digica cards!");
        return extraction;
    }

    @GetMapping("/get-previous-extraction")
    public @ResponseBody DigicaWikiExtraction getExtraction() {
        LOGGER.info("Request to get digica cards received!");
        DigicaWikiExtraction extraction = service.getExtraction();
        LOGGER.info("Done getting digica cards!");
        return extraction;
    }
}
