package com.noboruu.digica.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class CardSetDTO {
    @JsonIgnore
    private Long id;
    private String code;
    private List<CardDTO> cards;
}
