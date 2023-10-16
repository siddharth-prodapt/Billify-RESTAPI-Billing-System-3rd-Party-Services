package com.prodapt.billingsystem.api.plans.entity;

import com.prodapt.billingsystem.api.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@Entity
@Table(name="plans")
public class Plan {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator
    @Column(unique = true)
    private UUID uuid = UUID.randomUUID();

    private String name;
    private String planFor;
    private String price;
    private String maxPersons;
    private String createdAt;
    private String modifiedAt;
    private boolean isAvailable;
    private String validity;
    private String validityType;
    private PlanType planType;
}
