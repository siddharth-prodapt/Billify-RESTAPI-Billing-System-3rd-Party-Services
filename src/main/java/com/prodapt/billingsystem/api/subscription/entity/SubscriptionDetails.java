package com.prodapt.billingsystem.api.subscription.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
public class SubscriptionDetails {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator
    @Column(unique = true)
    private UUID uuid = UUID.randomUUID();

    private Long userId;
    private Long planId;

    private String createdAt;
    private String expiryAt;
    private String nextBillingDate;
    private Double nextBillingPrice;
    private boolean isActive ;

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis()).toString();
    }

}
