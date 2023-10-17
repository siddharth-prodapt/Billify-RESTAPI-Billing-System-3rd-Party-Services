package com.prodapt.billingsystem.api.auth.services;

import com.prodapt.billingsystem.api.auth.dto.*;
import com.prodapt.billingsystem.api.user.entity.User;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;


public interface AuthenticationService {
        public SignupResponse signup(SignupRequest signupRequest);

        public JwtAuthenticationResponse signin( SigninRequest signinRequest);

        public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    public String forgotPasswordReset(String emailId);
    }
