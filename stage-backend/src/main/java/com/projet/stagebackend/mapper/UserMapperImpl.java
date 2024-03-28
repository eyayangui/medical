package com.projet.stagebackend.mapper;

import com.projet.stagebackend.dto.ExamenDTO;
import com.projet.stagebackend.dto.UserDto;
import com.projet.stagebackend.entity.Examen;
import com.projet.stagebackend.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserMapperImpl {
    public UserDto fromUser(User user){
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);

        if (user.getMedicalRecords() != null) {
            userDto.setMedical_record_id(user.getMedicalRecords().getIdRecord());
        }

        return userDto;
    }
    public User fromUserDto(UserDto userDto){
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return user;
    }


}
