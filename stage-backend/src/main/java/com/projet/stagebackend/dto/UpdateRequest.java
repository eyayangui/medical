package com.projet.stagebackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateRequest {
    private List<Long> responseIds;
    private String champResponseText;
}
