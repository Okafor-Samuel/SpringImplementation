package com.example.sercuritydemo.Payload.RequestDto;

import com.example.sercuritydemo.Enum.Gender;
import com.example.sercuritydemo.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private Role role;
    private Gender gender;
}
