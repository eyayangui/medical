package com.projet.stagebackend.auth;


import com.projet.stagebackend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String firstname;
  private String lastname;
  private String email;
  private String password;
  private String cin;
  private String phoneNumber;
  private String gender;
  private String birthDate;
  private String adress;
  private String familySituation;
  private String medicalBackgroung;
  private Role role;
}
