package com.prodapt.billingsystem.api.user_subscription_details.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
public class UserSubscriptionDetails {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID uuid;
    private Long userId;
    private Long planId;

    @Column(name = "rate_per_day",precision = 8,scale = 2)
    private BigDecimal ratePerDay;

    private String createdAt;
    private String billingDate;
    private Boolean status; //true -ACTIVE

    private String modifiedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = String.valueOf(new Timestamp(System.currentTimeMillis()));
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedAt = String.valueOf(new Timestamp(System.currentTimeMillis()));
    }


}
