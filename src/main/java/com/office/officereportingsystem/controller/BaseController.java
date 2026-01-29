package com.office.officereportingsystem.controller;

import com.office.officereportingsystem.dto.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * BaseController to centralize API response construction.
 * All controllers should extend this class.
 */
public abstract class BaseController {

    /**
     * Returns a successful response with data.
     *
     * @param data        The response data
     * @param messageCode Message code for multilingual support
     */
    protected <T> ResponseEntity<ResponseDto<T>> success(T data, String messageCode) {
        ResponseDto<T> response = ResponseDto.<T>builder()
                .success(true)
                .messageCode(messageCode)
                .data(data)
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * Returns a successful response without data.
     *
     * @param messageCode Message code for multilingual support
     */
    protected ResponseEntity<ResponseDto<Void>> success(String messageCode) {
        ResponseDto<Void> response = ResponseDto.<Void>builder()
                .success(true)
                .messageCode(messageCode)
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * Returns an error response with status code.
     *
     * @param messageCode Message code for multilingual support
     * @param status      HTTP status
     */
    protected ResponseEntity<ResponseDto<Void>> error(String messageCode, HttpStatus status) {
        ResponseDto<Void> response = ResponseDto.<Void>builder()
                .success(false)
                .messageCode(messageCode)
                .build();
        return new ResponseEntity<>(response, status);
    }
}
