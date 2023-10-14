package com.prodapt.billingsystem.api.auth.services.impl;


import com.prodapt.billingsystem.api.auth.dto.JwtAuthenticationResponse;
import com.prodapt.billingsystem.api.auth.dto.RefreshTokenRequest;
import com.prodapt.billingsystem.api.auth.dto.SigninRequest;
import com.prodapt.billingsystem.api.auth.dto.SignupRequest;
import com.prodapt.billingsystem.api.auth.services.AuthenticationService;
import com.prodapt.billingsystem.api.auth.services.JwtService;
import com.prodapt.billingsystem.api.user.dao.UserRepository;
import com.prodapt.billingsystem.api.user.entity.Role;
import com.prodapt.billingsystem.api.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User signup(SignupRequest signupRequest) {
        User user = new User();
        user.setEmail(signupRequest.getEmail());

//        user.setFirstname(signupRequest.getFirstname());
//        user.setSecondname(signupRequest.getLastname());
        user.setName(signupRequest.getName());
        user.setRole(Role.ROLE_USER);
        user.setPassword( new BCryptPasswordEncoder().encode(signupRequest.getPassword()));

        return userRepository.save(user);
    }

    public JwtAuthenticationResponse signin(SigninRequest signinRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signinRequest.getEmail(), signinRequest.getPassword()));

        User user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(()-> new IllegalArgumentException("Invalid Email ID"));
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse();

        jwtResponse.setEmail(signinRequest.getEmail());
        jwtResponse.setToken(jwtToken);
        jwtResponse.setRefreshToken(refreshToken);

        return jwtResponse;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow( ()-> new UsernameNotFoundException("Email not registered") );

        if( jwtService.isTokenValid( refreshTokenRequest.getToken(), user)) {
            String jwtToken = jwtService.generateToken(user);

            JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse();

            jwtResponse.setEmail(userEmail);
            jwtResponse.setToken(jwtToken);
            jwtResponse.setRefreshToken( refreshTokenRequest.getToken());

            return jwtResponse;

        }
        return null;
    }
}

