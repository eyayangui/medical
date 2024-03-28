package com.projet.stagebackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idScore;

    @Column(nullable = false)
    private Integer totalScore;

    @OneToOne
    @JoinColumn(name = "examen_id", unique = true)
    private Examen examen;

}
