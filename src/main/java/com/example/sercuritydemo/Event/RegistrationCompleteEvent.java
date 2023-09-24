package com.example.sercuritydemo.Event;


import com.example.sercuritydemo.Entity.Users;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {
    private Users users;
    private String applicationUrl;


    public RegistrationCompleteEvent(Users users, String applicationUrl){
        super(users);
        this.users = users;
        this.applicationUrl = applicationUrl;
    }
}
