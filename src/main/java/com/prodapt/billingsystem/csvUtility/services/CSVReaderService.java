package com.prodapt.billingsystem.csvUtility.services;

import com.prodapt.billingsystem.csvUtility.AdminInvoiceList;

import java.util.List;

public interface CSVReaderService {

    public void readCSVFileAndSendEmail();

    public List<AdminInvoiceList> getDataFromCSV();
}
