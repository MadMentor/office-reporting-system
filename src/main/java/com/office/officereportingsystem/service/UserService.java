package com.office.officereportingsystem.service;

import com.office.officereportingsystem.entity.User;

import java.util.List;

public interface UserService {
   List<User> getAllUsers();
   long getTotalUsersCount();
   long getAdminCount();
}
