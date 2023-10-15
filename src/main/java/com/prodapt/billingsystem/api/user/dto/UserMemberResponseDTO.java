package com.prodapt.billingsystem.api.user.dto;

import com.prodapt.billingsystem.api.plans.entity.Plan;
import com.prodapt.billingsystem.api.user.entity.Role;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserMemberResponseDTO {
    private Long id;
    private String name;
    private String phoneNo;
    private String createdAt;
    private Role role;
    private Long parentUserId;

}
