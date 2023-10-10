package com.prodapt.billingsystem.api.auth.controller;


import com.prodapt.billingsystem.api.auth.dto.JwtAuthenticationResponse;
import com.prodapt.billingsystem.api.auth.dto.RefreshTokenRequest;
import com.prodapt.billingsystem.api.auth.dto.SigninRequest;
import com.prodapt.billingsystem.api.auth.dto.SignupRequest;
import com.prodapt.billingsystem.api.auth.services.AuthenticationService;
import com.prodapt.billingsystem.api.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignupRequest signupRequest){
        System.out.println("SignupReq: "+ signupRequest.getEmail()+signupRequest.getPassword());
        return new ResponseEntity<>(authenticationService.signup(signupRequest) , HttpStatus.OK);
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
