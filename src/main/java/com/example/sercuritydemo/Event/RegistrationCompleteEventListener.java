package com.example.sercuritydemo.Event;

import com.example.sercuritydemo.Entity.Users;
import com.example.sercuritydemo.Payload.ResponseDto.EmailDto;
import com.example.sercuritydemo.Service.ServiceImpl.AuthenticationServiceImpl;
import com.example.sercuritydemo.Service.ServiceImpl.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final AuthenticationServiceImpl authenticationService;
    private final EmailServiceImpl emailService;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //1. Get the newly registered user
        Users theUser = event.getUsers();
        //2. Create a verification token for the user
        String verificationToken = UUID.randomUUID().toString();
        //3. Save the verification token for the user
        authenticationService.saveUserVerificationToken(theUser, verificationToken);
        //4. Build the Url to be sent to the user
        String url =event.getApplicationUrl()+"/api/v1/register/verifyEmail?token="+verificationToken;
        String message= "<p> Hi, "+ theUser.getFirstName()+ " , </p>"+
                "<p> Thank you for registering with us, "+""+
                "Please, follow the link below to complete your registration.</p>"+
                "<a href=\""+ url+ "\">Verify your email to activate your account</a>"+
                "<p>Thank you <br> User Registration Portal Service.";
        //5. Send the email
        var emailDto = EmailDto.builder()
                .recipient(theUser.getEmail())
                .subject("EMAIL VERIFICATION")
                .messageBody(message)
                .build();
        emailService.sendEmailAlert(emailDto);
    }
}
