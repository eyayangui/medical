package com.projet.stagebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SelectedResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSelected ;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private String response;

    @ManyToOne
    @JoinColumn(name = "reponse_id")
    @JsonIgnore
    private Reponse reponse;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonIgnore
    private Question question;

    @ManyToOne
    @JoinColumn(name = "examen_id")
    @JsonIgnore
    private Examen examen;

    @ManyToOne
    @JoinColumn(name = "consultation_id")
    @JsonIgnore
    private Consultation consultation;
}
