package com.prodapt.billingsystem.api.invoice.dto;

import com.prodapt.billingsystem.api.plans.dto.InvoicePlanDetailsDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class InvoiceResDTO {
    private Long invoiceId;
    private UUID invoiceUuid;
    private String name ;
    private String email;
    private String noOfPlansSubscribed;
    private List<InvoicePlanDetailsDTO> subscribedPlanDetails= new ArrayList<>();
    private String sumTotal;
    private String amount;
    private String billDate;
    private String billDueDate;
    private String discount;
    private String paymentStatus;
    private String paymentDate;
}
