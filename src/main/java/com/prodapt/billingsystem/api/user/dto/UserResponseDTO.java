package com.prodapt.billingsystem.api.user.dto;

import com.prodapt.billingsystem.api.user.entity.Role;
import lombok.Data;

import java.util.UUID;

@Data
public class UserResponseDTO {
    private UUID uuid;
    private String email;
    private String isAvailable;
    private String name;
    private String phoneNo;
    private Role role;
    private String accountStatus;
    private String createdAt;
    private boolean invoiceGenerated;
    private boolean accountAccess;
}
