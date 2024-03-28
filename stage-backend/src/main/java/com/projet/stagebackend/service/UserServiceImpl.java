package com.projet.stagebackend.service;

import com.projet.stagebackend.dto.UserDto;
import com.projet.stagebackend.entity.Role;
import com.projet.stagebackend.entity.User;
import com.projet.stagebackend.mapper.UserMapperImpl;
import com.projet.stagebackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private UserMapperImpl dtoMapper;

    @Override
    public List<UserDto> listUsers(Role role) {
        List<User> users = userRepository.findByRoleWithMedicalRecords(role); // Add a custom repository method to fetch with medicalRecords
        List<UserDto> userDtos = users.stream()
                .map(user -> dtoMapper.fromUser(user))
                .collect(Collectors.toList());
        return userDtos;
    }

    public User findById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }


}
