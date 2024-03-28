package com.projet.stagebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String firstname;
  private String lastname;
  @Column(unique = true)
  private String email;
  private String password;
  @Column(unique = true)
  private String cin;
  @Column(unique = true)
  private String phoneNumber;
  private String gender;
  private String birthDate;
  private String adress;
  private String familySituation;
  private String medicalBackgroung;
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "medical_record_id")
  private MedicalRecords medicalRecords;

  @Enumerated(EnumType.STRING)
  private Role role;

  // One-to-many relationship for patients (users of role "patient")
  @OneToMany(mappedBy = "patient", cascade = CascadeType.REMOVE)
  @JsonIgnore
  private List<Consultation> patientConsultations;
/*
  // One-to-many relationship for medical professionals (users of role "medcin")
  @OneToMany(mappedBy = "medicalProfessional")
  private List<Consultation> medicalConsultations;*/

  /*@OneToMany(mappedBy = "user")
  private List<Token> tokens;*/
  @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
  private List<Token> tokens;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<SelectedResponse> selectedResponses = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
