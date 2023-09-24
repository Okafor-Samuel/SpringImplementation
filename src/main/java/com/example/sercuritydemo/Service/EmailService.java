package com.example.sercuritydemo.Service;

import com.example.sercuritydemo.Payload.ResponseDto.EmailDto;

public interface EmailService {
    void sendEmailAlert(EmailDto emailDto);
}
