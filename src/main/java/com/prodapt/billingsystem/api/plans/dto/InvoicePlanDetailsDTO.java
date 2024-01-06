package com.prodapt.billingsystem.api.plans.dto;

import lombok.Data;

@Data
public class InvoicePlanDetailsDTO {
    private String planName;
    private String planAmount;
    private String ratePerDay;
    private String activationDate;
}
