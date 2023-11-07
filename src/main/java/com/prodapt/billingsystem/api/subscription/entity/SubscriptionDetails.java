package com.prodapt.billingsystem.api.subscription.entity;

import com.prodapt.billingsystem.api.plans.entity.Plan;
import com.prodapt.billingsystem.api.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Data
@Entity
public class SubscriptionDetails {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator
    private UUID uuid = UUID.randomUUID();

    private Long userId;
    private Long planId;


//    @ManyToMany(cascade = CascadeType.ALL)
//    private List<User> user;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    private List<Plan> plan;


    private Timestamp createdAt;
    private Timestamp expiryAt;
    private boolean isActive = true;

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
    }

}
