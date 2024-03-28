package com.projet.stagebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idConsultation;

    @OneToMany(mappedBy = "consultation", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<SelectedResponse> selectedResponses;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonIgnore
    private User patient;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateConsultation;

    @ManyToOne
    @JoinColumn(name = "type_examen_id")
    @JsonIgnore
    private TypeExamen typeExamen;

}
