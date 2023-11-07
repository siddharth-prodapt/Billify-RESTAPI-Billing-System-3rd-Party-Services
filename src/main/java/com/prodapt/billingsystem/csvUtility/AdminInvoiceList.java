package com.prodapt.billingsystem.csvUtility;

import lombok.Data;

@Data
public class AdminInvoiceList {
    private Long id;
    private String name;
    private String email ;
    private String phoneNo;
    private float amount;
}
