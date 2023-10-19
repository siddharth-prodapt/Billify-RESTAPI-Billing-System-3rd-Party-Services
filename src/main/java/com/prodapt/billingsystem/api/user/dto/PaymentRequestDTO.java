package com.prodapt.billingsystem.api.user.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PaymentRequestDTO {
    private UUID userUuid;
    private UUID invoiceUuid;
    private String paymentTime;
    private int amount;
}
