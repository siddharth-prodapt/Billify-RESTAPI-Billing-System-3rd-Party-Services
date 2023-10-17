package com.prodapt.billingsystem.api.invoice.dto;

import lombok.Data;

@Data
public class InvoiceDB {
    private Long userId;
    private Long subsID;
    private Float amount;
    private int noOfPlans ;
}
