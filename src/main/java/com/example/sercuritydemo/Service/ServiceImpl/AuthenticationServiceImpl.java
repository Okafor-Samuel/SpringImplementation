package com.example.sercuritydemo.Service.ServiceImpl;

import com.example.sercuritydemo.Configuration.JwtService;
import com.example.sercuritydemo.Entity.Users;
import com.example.sercuritydemo.Entity.VerificationToken;
import com.example.sercuritydemo.Exception.InvalidCredentialsException;
import com.example.sercuritydemo.Exception.UserAlreadyExistsException;
import com.example.sercuritydemo.Payload.RequestDto.AuthenticationRequest;
import com.example.sercuritydemo.Payload.RequestDto.UsersDto;
import com.example.sercuritydemo.Payload.ResponseDto.AuthenticationResponse;
import com.example.sercuritydemo.Repository.UsersRepository;
import com.example.sercuritydemo.Repository.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl {
    private final UsersRepository usersRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final VerificationTokenRepository tokenRepository;
    //Authenticate

    public ResponseEntity<Users> signUp(UsersDto usersDto) {
        if (usersRepository.findByEmail(usersDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }
        var users = usersRepository.save(new Users(usersDto));
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    public Optional<Users> loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByEmail(username);
    }

    //TOKEN
    public void saveUserVerificationToken(Users users, String token) {
        var verificationToken = new VerificationToken(token, users);
        verificationTokenRepository.save(verificationToken);
    }
    @Transactional
    public String validateToken(String theToken) {
        VerificationToken token = verificationTokenRepository.findByToken(theToken);
        if (token == null) {
            return "Invalid verification token";
        }

        Users users = token.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            verificationTokenRepository.delete(token);
            return "Token already expired";
        }
        users.setEnabled(true);
        usersRepository.save(users);
        return "Valid";
    }




    //Login
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate
                ( new UsernamePasswordAuthenticationToken
                        (authenticationRequest.getPassword(), authenticationRequest.getPassword()));
        var user = usersRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(()-> new InvalidCredentialsException("Login failed; Invalid user ID or password."));
        var jwtToken = jwtService.generateToken((UserDetails) user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }























    //logout
    //forgot password
    //change password




}