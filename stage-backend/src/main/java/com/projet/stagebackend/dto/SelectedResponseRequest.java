package com.projet.stagebackend.dto;

import com.projet.stagebackend.entity.ReponseType;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SelectedResponseRequest {
    private Integer userId;
    private List<Long> responseIds;
    private Long questionId;
    private Integer examenId;
    private String champResponseText;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}
