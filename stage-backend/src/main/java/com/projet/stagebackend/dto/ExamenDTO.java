package com.projet.stagebackend.dto;

import com.projet.stagebackend.entity.TypeExamen;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamenDTO {
    private Integer idExamen;
    private String libelle;
    private String typeExamen;
    private String typeExamenName;


}
