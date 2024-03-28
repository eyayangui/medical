package com.projet.stagebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Examen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idExamen;
    @Column(unique = true)
    private String libelle;


    @ManyToOne
    @JoinColumn(name = "type_examen_id")
    @NotNull
    @JsonIgnore
    private TypeExamen typeExamen;

    @OneToMany(mappedBy = "examen", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Question> questions;

    @JsonIgnore
    @OneToOne(mappedBy = "examen", cascade = CascadeType.ALL, orphanRemoval = true)
    private Score score;


}
