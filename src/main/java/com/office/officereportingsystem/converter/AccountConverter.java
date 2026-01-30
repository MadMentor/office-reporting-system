package com.office.officereportingsystem.converter;

import com.office.officereportingsystem.dto.response.AccountResponseDto;
import com.office.officereportingsystem.entity.User;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccountConverter extends AbstractConverter<AccountResponseDto, User> {
    @Override
    public AccountResponseDto toDto(User user) throws IOException {
        return AccountResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    @Override
    public User toEntity(AccountResponseDto accountResponseDto) {
        User user = new User();

        user.setId(accountResponseDto.getId());
        user.setFirstName(accountResponseDto.getFirstName());
        user.setMiddleName(accountResponseDto.getMiddleName());
        user.setLastName(accountResponseDto.getLastName());
        user.setEmail(accountResponseDto.getEmail());

        return user;
    }
}
