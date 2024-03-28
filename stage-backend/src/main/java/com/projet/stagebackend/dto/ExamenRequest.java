package com.projet.stagebackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExamenRequest {
    private Integer idExamen;
    private String libelle;
    @NotNull
    private String typeExamen;
}
