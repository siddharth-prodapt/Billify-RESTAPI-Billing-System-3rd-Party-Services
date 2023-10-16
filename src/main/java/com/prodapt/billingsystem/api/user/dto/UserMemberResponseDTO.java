package com.prodapt.billingsystem.api.user.dto;

import com.prodapt.billingsystem.api.plans.entity.Plan;
import com.prodapt.billingsystem.api.user.entity.Role;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class UserMemberResponseDTO {
    private String name;
    private String phoneNo;
    private String createdAt;
    private Role role;
    private UUID memberUuid;

}
