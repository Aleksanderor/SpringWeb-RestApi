package com.crud.tasks.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TrelloAttachmentsByTypeDto {
    @JsonProperty("trello")
    private TrelloDto trello;
}