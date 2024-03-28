package com.projet.stagebackend.dto;

import lombok.Data;

@Data
public class ExamenResponse {
    private Integer idExamen;
    private String libelle;
    private String typeExamen;  // Keep the typeExamen field for value
    private String typeExamenName;  // Add a new field for name
    private Integer typeExamenId;
}
