package com.noboruu.digica.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class CardDTO {

    @JsonIgnore
    private Long id;
    @JsonIgnore
    private Long cardSetId;
    private String code;
    private String name;
    private String artUrl;
    private CardTypeEnum cardType;
    private List<CardEffectDTO> cardEffects = new ArrayList<>();
}