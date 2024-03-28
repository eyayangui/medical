package com.projet.stagebackend.dto;

import com.projet.stagebackend.entity.Examen;
import com.projet.stagebackend.entity.Question;
import lombok.Data;

import java.util.List;

@Data
public class ExamQuestionDTO {
    private Examen examen;
    private List<Question> questions;
}
