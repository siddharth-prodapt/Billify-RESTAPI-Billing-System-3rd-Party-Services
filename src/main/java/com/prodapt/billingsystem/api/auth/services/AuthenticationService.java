package com.prodapt.billingsystem.api.auth.services;

import com.prodapt.billingsystem.api.auth.dto.JwtAuthenticationResponse;
import com.prodapt.billingsystem.api.auth.dto.RefreshTokenRequest;
import com.prodapt.billingsystem.api.auth.dto.SigninRequest;
import com.prodapt.billingsystem.api.auth.dto.SignupRequest;
import com.prodapt.billingsystem.api.user.entity.User;


    public interface AuthenticationService {
        public User signup(SignupRequest signupRequest);

        public JwtAuthenticationResponse signin( SigninRequest signinRequest);

        public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
    }
