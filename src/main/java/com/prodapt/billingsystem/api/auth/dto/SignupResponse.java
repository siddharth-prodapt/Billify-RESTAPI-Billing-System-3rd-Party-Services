package com.prodapt.billingsystem.api.auth.dto;

import lombok.Data;
import lombok.Setter;

import java.util.UUID;

@Data
public class SignupResponse {
    private UUID uuid;
    private String name;
    private String email;
    private String createdAt;
}
