package com.prodapt.billingsystem.api.plans.dto;

import com.prodapt.billingsystem.api.plans.entity.PlanType;
import jakarta.persistence.Column;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
public class PlanResponseDTO {
    private UUID uuid;
    private String name;
    private String planFor;
    private String price;
    private String maxPersons;

    private String validity;
    private String validityType;
    private PlanType planType;

    private String imgUrl;
    private String internet;
    private String speed;
}
