package com.projet.stagebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeExamen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "typeExamen", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Examen> examens;

    @OneToMany(mappedBy = "typeExamen", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Consultation> consultations;
}
