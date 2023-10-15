package com.prodapt.billingsystem.api.auth.dto;

import lombok.Data;

@Data
public class SigninRequest {
    private String email;
    private String password;
}
