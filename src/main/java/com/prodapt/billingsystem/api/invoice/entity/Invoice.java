package com.prodapt.billingsystem.api.invoice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

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
    private String emailId;
    private Long subsDescId;  //subscribeDetailsId
    private int nosOfPlans;
    private Float amount;

    private boolean paymentStatus;
    private String status;
    private boolean isAvailable  = true;
    private String paymentDate;
    private String dueDate;

    private boolean isEmailSent = false; //true : sent
}
