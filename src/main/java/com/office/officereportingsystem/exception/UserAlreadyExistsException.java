package com.office.officereportingsystem.exception;

import com.office.officereportingsystem.dto.request.AdminCreateRequestDto;

public class UserAlreadyExistsException extends RuntimeException {
//
//    private final String messageCode;
//
//    public UserAlreadyExistsException(String messageCode) {
//        super(messageCode);
//        this.messageCode = messageCode;
//    }
//
//    public String getMessageCode() {
//        return messageCode;
//    }
    private final AdminCreateRequestDto adminData;

    public UserAlreadyExistsException(String message, AdminCreateRequestDto adminData) {
        super(message);
        this.adminData = adminData;
    }

    public AdminCreateRequestDto getAdminData() {
        return adminData;
    }
}
