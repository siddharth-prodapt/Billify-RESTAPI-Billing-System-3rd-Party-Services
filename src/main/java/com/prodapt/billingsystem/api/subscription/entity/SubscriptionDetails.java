package com.prodapt.billingsystem.api.subscription.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class SubscriptionDetails {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator
    private UUID uuid = UUID.randomUUID();

    private Long userId;
    private Long planId;
    private Timestamp createdAt;
    private Timestamp expiryAt;
    private boolean isActive;

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
    }

//    @PreUpdate
//    protected void onUpdate() {
//        updatedAt = new Date();
//    }
}
