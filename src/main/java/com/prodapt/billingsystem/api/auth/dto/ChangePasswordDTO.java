package com.prodapt.billingsystem.api.auth.dto;

import lombok.Data;

@Data
public class ChangePasswordDTO {
    private String email;
    private String password;
}
