package com.prodapt.billingsystem.api.auth.controller;


import com.prodapt.billingsystem.api.auth.dto.*;
import com.prodapt.billingsystem.api.auth.services.AuthenticationService;
import com.prodapt.billingsystem.api.user.entity.User;
import com.prodapt.billingsystem.email.EmailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private EmailServices emailServices;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest signupRequest){
        System.out.println("SignupReq: "+ signupRequest.getEmail()+signupRequest.getPassword());
        return new ResponseEntity<>(authenticationService.signup(signupRequest) , HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest signinRequest){
        System.out.println("signin");
        return new ResponseEntity<JwtAuthenticationResponse>(authenticationService.signin(signinRequest), HttpStatus.OK);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return new ResponseEntity<>(authenticationService.refreshToken(refreshTokenRequest), HttpStatus.OK);
    }

    @PostMapping("/forgot-password/{emailId}")
    public ResponseEntity<String> forgotPassword(@PathVariable String emailId){
        System.out.println("Forgot Password controller");
        return new ResponseEntity<>(authenticationService.forgotPasswordReset(emailId), HttpStatus.OK );
    }
}
