package com.projet.stagebackend.service;

import com.projet.stagebackend.dto.UserDto;
import com.projet.stagebackend.entity.Role;
import com.projet.stagebackend.entity.User;

import java.util.List;

public interface UserService {
    List<UserDto> listUsers(Role role);
    User findById(Integer id);
}
