package com.projet.stagebackend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseConsultation {
    private Long idSelected;
    private Long idReponse;
    private Long idQuestion;
    private Date date;
    private String response;
}
