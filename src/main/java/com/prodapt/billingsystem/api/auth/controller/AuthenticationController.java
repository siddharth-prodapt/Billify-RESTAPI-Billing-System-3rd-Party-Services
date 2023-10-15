package com.prodapt.billingsystem.api.auth.controller;


import com.prodapt.billingsystem.api.auth.dto.*;
import com.prodapt.billingsystem.api.auth.services.AuthenticationService;
import com.prodapt.billingsystem.api.user.entity.User;
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

}
