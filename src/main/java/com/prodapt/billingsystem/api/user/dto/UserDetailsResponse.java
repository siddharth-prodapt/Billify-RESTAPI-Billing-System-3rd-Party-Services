package com.prodapt.billingsystem.api.user.dto;

import com.prodapt.billingsystem.api.plans.entity.Plan;
import com.prodapt.billingsystem.api.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsResponse {
    private Long id;
    private String name;

    private String phoneNo;
    private String email;
//    private String dateOfBirth;
//    private String pincode;
//    private String city;
//    private String state;
//    private String country;
    private boolean isParentUser;
//    private String createdAt;
//    private String modifiedAt;
    private Role role; //user//parentuser //admin
    private Long parentUserId;
    private boolean isAvailable;
    private List<Plan> subscribedPlans;
}
