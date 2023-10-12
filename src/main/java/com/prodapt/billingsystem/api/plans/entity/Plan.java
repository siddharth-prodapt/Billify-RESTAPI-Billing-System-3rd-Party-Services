package com.prodapt.billingsystem.api.plans.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="plans")
public class Plan {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String planFor;
    private String price;
    private String maxPersons;
    private String createdAt;
    private String modifiedAt;
    private boolean isAvailable;
    private String validity;
    private String durationType;
    private PlanType planType;
}
