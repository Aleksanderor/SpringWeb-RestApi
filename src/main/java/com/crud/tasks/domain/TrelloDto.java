package com.crud.tasks.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class TrelloDto {
    @JsonProperty("board")
    private int number;
    @JsonProperty("card")
    private int card;
}