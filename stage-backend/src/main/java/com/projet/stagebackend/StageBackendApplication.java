package com.projet.stagebackend;

import com.projet.stagebackend.auth.AuthenticationService;
import com.projet.stagebackend.auth.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.projet.stagebackend.entity.Role.ADMIN;
import static com.projet.stagebackend.entity.Role.PATIENT;


@SpringBootApplication
public class StageBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(StageBackendApplication.class, args);
    }



}
