package com.scm.payload.response;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ForgetPasswordResponse 
{
    private String message;
    private HttpStatus status;
}
