package com.prodapt.billingsystem.api.invoice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class InvoiceResponseDTO {
    private UUID invoiceUuid;

    private String userId;
    private String emailId;
    private Float amount;
    private int noOfPlans ;
    private boolean paymentStatus;
}
