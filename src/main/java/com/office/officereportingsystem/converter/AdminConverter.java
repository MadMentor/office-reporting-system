package com.office.officereportingsystem.converter;

import com.office.officereportingsystem.dto.response.AdminResponseDto;
import com.office.officereportingsystem.entity.User;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AdminConverter extends AbstractConverter<AdminResponseDto, User> {
    @Override
    public AdminResponseDto toDto(User user) throws IOException {
        return AdminResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    @Override
    public User toEntity(AdminResponseDto adminResponseDto) {
        User user = new User();

        user.setId(adminResponseDto.getId());
        user.setFirstName(adminResponseDto.getFirstName());
        user.setMiddleName(adminResponseDto.getMiddleName());
        user.setLastName(adminResponseDto.getLastName());
        user.setEmail(adminResponseDto.getEmail());

        return user;
    }
}
