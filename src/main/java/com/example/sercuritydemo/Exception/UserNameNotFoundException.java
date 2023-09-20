package com.example.sercuritydemo.Exception;

public class UserNameNotFoundException extends RuntimeException{
    public UserNameNotFoundException(String message) {
        super(message);
    }
}
