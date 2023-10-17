package com.prodapt.billingsystem.api.invoice.controller;

import com.prodapt.billingsystem.api.invoice.entity.Invoice;
import com.prodapt.billingsystem.api.invoice.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/admin/invoice/{userUuid}")
    public ResponseEntity<Invoice> generateInvoice(@PathVariable UUID userUuid){
        System.out.println("Generated Invoice controller");
        Invoice invoice = invoiceService.generateInvoiceByUuid(userUuid);
        return new ResponseEntity<>(invoice, HttpStatus.OK);
    }

}
