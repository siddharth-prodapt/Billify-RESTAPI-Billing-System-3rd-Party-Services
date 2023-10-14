package com.prodapt.billingsystem.api.user.dto;

import lombok.Data;

@Data
public class UserMemberRequestDTO {
    private Long parentUserId;
    private String phoneNumber;
    private String name;
    private boolean isAvailable;
}
