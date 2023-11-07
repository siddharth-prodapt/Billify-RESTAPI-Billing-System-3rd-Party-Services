package com.prodapt.billingsystem.csvUtility.services;

import com.prodapt.billingsystem.api.invoice.repository.InvoiceRepo;
import com.prodapt.billingsystem.csvUtility.AdminInvoiceList;
import com.prodapt.billingsystem.email.EmailServiceInterface;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVReaderServiceImpl implements CSVReaderService {


    @Autowired
    private EmailServiceInterface emailService;

    @Autowired
    private InvoiceRepo invoiceRepo;

    public void readCSVFileAndSendEmail() {

        System.out.println("Read CSV File");
        try {
            Reader in = new FileReader("C:\\Users\\siddharth.sp\\Downloads\\Book.csv");

            Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);

            for (CSVRecord record : records) {
                System.out.println("Record: "+record);

                String name = record.get("Name");
                String email = record.get("Email");
                String phoneNo = record.get("PhoneNo");
                String amount = record.get("Amount");

                emailService.sendInvoiceFromCSV(email, phoneNo, amount, name);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public  List<AdminInvoiceList> getDataFromCSV() {

        System.out.println("Read CSV File");
        List<AdminInvoiceList> invoiceList = new ArrayList<>();
        try {
            Reader in = new FileReader("C:\\Users\\siddharth.sp\\Downloads\\Book.csv");

            Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);

            for (CSVRecord record : records) {
                System.out.println("Record: "+record);

                String sno = record.get("Sno");
                String email = record.get("Email");
                String phoneNo = record.get("PhoneNo");
                String amount = record.get("Amount");
                String name = record.get("Name");

                AdminInvoiceList invoice = new AdminInvoiceList();

                invoice.setId(Long.valueOf(sno));
                invoice.setAmount(Float.valueOf(amount));
                invoice.setEmail(email);
                invoice.setPhoneNo(phoneNo);
                invoice.setName(name);

                invoiceList.add(invoice);

//                emailService.sendInvoiceFromCSV(email, phoneNo, amount);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return invoiceList;
    }











}
