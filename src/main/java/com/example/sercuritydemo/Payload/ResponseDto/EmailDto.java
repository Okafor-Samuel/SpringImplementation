package com.example.sercuritydemo.Payload.ResponseDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDto {
    private String recipient;
    private String messageBody;
    private String subject;
    private String attachment;
}
