package com.prodapt.billingsystem.csvUtility;

import lombok.Data;

@Data
public class AdminInvoiceList {
    private Long id;
    private String email ;
    private Long phoneNo;
    private float amount;
}
