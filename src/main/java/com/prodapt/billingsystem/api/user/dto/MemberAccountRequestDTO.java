package com.prodapt.billingsystem.api.user.dto;


import lombok.Data;

@Data
public class MemberAccountRequestDTO {
    String parentUuid;
    String name;
    String phoneNo;
    String gender;
}
