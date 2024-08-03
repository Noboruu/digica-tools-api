package com.noboruu.digica.extractor.internal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Card {

    @JsonProperty("code")
    private String code;
    @JsonProperty("name")
    private String name;
    @JsonProperty("artUrl")
    private String artUrl;

    public Card() {}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtUrl() {
        return artUrl;
    }

    public void setArtUrl(String artUrl) {
        this.artUrl = artUrl;
    }
}