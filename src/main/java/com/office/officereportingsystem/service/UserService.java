package com.office.officereportingsystem.service;

import com.office.officereportingsystem.dto.request.UserCreateRequestDtos;
import com.office.officereportingsystem.entity.User;
import com.office.officereportingsystem.exception.UserAlreadyExistsException;

import java.util.List;

public interface UserService {
   List<User> getAllUsers();
   long getTotalUsersCount();
   long getAdminCount();
}
