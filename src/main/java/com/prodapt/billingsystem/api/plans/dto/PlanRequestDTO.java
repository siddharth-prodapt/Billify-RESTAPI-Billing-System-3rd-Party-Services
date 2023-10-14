package com.prodapt.billingsystem.api.plans.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PlanRequestDTO {
    private Long id;  //parent user id
    private Long subscribedPlanId; //subscribed plan ids
}
