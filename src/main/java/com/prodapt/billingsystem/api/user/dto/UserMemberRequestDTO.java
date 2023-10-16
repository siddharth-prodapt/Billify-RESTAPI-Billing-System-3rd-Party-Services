package com.prodapt.billingsystem.api.user.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserMemberRequestDTO {
    private UUID userUuid;
    private String phoneNumber;
    private String name;
}
