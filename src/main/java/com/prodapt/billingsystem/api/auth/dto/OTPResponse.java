package com.prodapt.billingsystem.api.auth.dto;

import lombok.Data;

@Data
public class OTPResponse {
    private String email;
    private String otp;
}
