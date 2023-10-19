package com.prodapt.billingsystem.api.invoice.dto;

import com.prodapt.billingsystem.api.plans.dto.PlanResponseDTO;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class InvoiceResponseDTO {
    private UUID invoiceUuid;

    private String userId;
    private String emailId;
    private Float amount;
    private int noOfPlans ;
    private boolean paymentStatus;

    private String dueDate;
    private String paymentDate;

    private List<PlanResponseDTO> subscribedPlans;

}
