package com.prodapt.billingsystem.api.plans.dto;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class PlanRequestDTO {
    private UUID id;  //parent user id
    private UUID subscribedPlanId; //subscribed plan ids
}
