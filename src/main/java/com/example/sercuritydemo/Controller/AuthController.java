package com.example.sercuritydemo.Controller;

import com.example.sercuritydemo.Entity.Users;
import com.example.sercuritydemo.Entity.VerificationToken;
import com.example.sercuritydemo.Event.RegistrationCompleteEvent;
import com.example.sercuritydemo.Payload.RequestDto.AuthenticationRequest;
import com.example.sercuritydemo.Payload.RequestDto.UsersDto;
import com.example.sercuritydemo.Payload.ResponseDto.AuthenticationResponse;
import com.example.sercuritydemo.Repository.VerificationTokenRepository;
import com.example.sercuritydemo.Service.ServiceImpl.AuthenticationServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/register")
public class AuthController {
    private final AuthenticationServiceImpl authenticationService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenRepository verificationTokenRepository;

    @PostMapping("/signup")
    public ResponseEntity<String> register (@RequestBody UsersDto usersDto, final HttpServletRequest request){
        var users = authenticationService.signUp(usersDto);
        // Publish registration event
        publisher.publishEvent(new RegistrationCompleteEvent(users.getBody(), applicationUrl(request)));
        return new ResponseEntity<>("Success! Please check your email to complete your registration", HttpStatus.OK);
    }
    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token){
        VerificationToken theToken = verificationTokenRepository.findByToken(token);
        if(theToken.getUser().isEnabled()){
            return "This account has already been verified, please login";
        }
        String verificationResult = authenticationService.validateToken(token);
        if(verificationResult.equalsIgnoreCase("Valid")){
            return "Email verified successfully, Please login";
        }return "Invalid verification token";
    }

    public String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest){
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));

    }


}
