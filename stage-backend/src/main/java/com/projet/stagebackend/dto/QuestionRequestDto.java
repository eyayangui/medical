package com.projet.stagebackend.dto;

import com.projet.stagebackend.entity.ReponseType;
import lombok.Data;

import java.util.List;

@Data
public class QuestionRequestDto {
    private Long id;
    private String label;
    private List<String> responses;
    private ReponseType responseType;
    private List<Long> idReponses;
}
