package com.prodapt.billingsystem.api.invoice.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator
    private UUID uuid = UUID.randomUUID();

    private Long userId;
    private String planId; //multiple Plans as CSV

    private String emailId;
    private Long subsDescId;  //subscribeDetailsId

    private int nosOfPlans;
    private float sumTotal;
    private int amount;

    private boolean paymentStatus;
    private String status; // invoice status PENDING
    private boolean isAvailable  = true;
    private String paymentDate;

    private String dueDate; //paymentDate + 10 days

    private boolean isEmailSent = false; //true : sent

    private List<Long> subscribedPlansId = new ArrayList<>();

    private String createdAt;
    private String modifiedAt;

}
