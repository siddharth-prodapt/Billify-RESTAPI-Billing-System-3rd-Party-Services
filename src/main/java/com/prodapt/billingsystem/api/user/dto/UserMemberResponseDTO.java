package com.prodapt.billingsystem.api.user.dto;

import com.prodapt.billingsystem.api.user.entity.Role;
import lombok.Data;

@Data
public class UserMemberResponseDTO {
    private Long id;
    private String name;
    private String phoneNo;
    private String createdAt;
    private Role role;
    private Long parentUserId;
}
