package com.projet.stagebackend.controller;

import com.projet.stagebackend.dto.UserDto;
import com.projet.stagebackend.entity.Role;
import com.projet.stagebackend.entity.User;
import com.projet.stagebackend.mapper.UserMapperImpl;
import com.projet.stagebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserContoller {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapperImpl userMapper;


    @GetMapping("/by-role")
    public List<UserDto> getUsersByRole(@RequestParam Role role) {
        return userService.listUsers(role);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id) {
        User user = userService.findById(id);

        if (user != null) {
            UserDto userDto = userMapper.fromUser(user);
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
