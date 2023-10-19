package com.prodapt.billingsystem.api.invoice.dto;

import com.prodapt.billingsystem.api.plans.dto.PlanResponseDTO;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class InvoicePDFTemplateDTO {

    private Long invoiceId;
    private String userId;
    private String username;
    private String phoneNo;
    private String city;
    private String state;
    private String country;
    private String pincode;
    private String emailId;
    private Float amount;
    private int noOfPlans ;
    private boolean paymentStatus;

    private List<PlanResponseDTO> subscribedPlans;
}
