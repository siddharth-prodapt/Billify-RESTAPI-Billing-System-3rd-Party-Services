package com.prodapt.billingsystem.api.user.dto;

import com.prodapt.billingsystem.api.user.entity.Role;
import lombok.Data;

@Data
public class UserDetailsRequest {
    private String email;
    private String phoneNo;
    private String dateOfBirth;
    private String pincode;
    private String city;
    private String state;
    private String country;
    private boolean isParentUser;
    private Role role;

}
