package com.prodapt.billingsystem.api.membersubscription.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class MemberAccountSubsRequestDTO {
    private UUID uuid;
    private String name;
}
