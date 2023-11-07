package com.prodapt.billingsystem.api.subscription.entity.dto;

import com.prodapt.billingsystem.api.plans.dto.PlanResponseDTO;
import com.prodapt.billingsystem.api.plans.entity.Plan;
import com.prodapt.billingsystem.api.plans.entity.PlanType;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class SubscriptionResponseDTO {

    private UUID userUuid;
    private String email;

    private List<PlanResponseDTO> subscribedPlans;
}
