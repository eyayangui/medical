package com.projet.stagebackend.controller;

import com.projet.stagebackend.dto.UserDto;
import com.projet.stagebackend.entity.Role;
import com.projet.stagebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patient")
@PreAuthorize("hasRole('PATIENT')")
public class PatientController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String patientProfile() {
        return "Patient Profile";
    }

    @GetMapping("/users/by-role")
    public List<UserDto> getUsersByRole(@RequestParam Role role) {
        return userService.listUsers(role);
    }
}
