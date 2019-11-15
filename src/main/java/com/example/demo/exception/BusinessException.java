package com.example.demo.exception;

import lombok.Getter;

@Getter
public class BusinessException extends Exception{
    private int code;
    private String message;

    public BusinessException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException(String message) {
        this.message = message;
    }
}
