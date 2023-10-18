package com.prodapt.billingsystem.api.admin.controller;

import com.prodapt.billingsystem.api.invoice.entity.Invoice;
import com.prodapt.billingsystem.api.invoice.services.InvoiceService;
import com.prodapt.billingsystem.api.plans.services.PlanService;
import com.prodapt.billingsystem.api.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private UserService userService;
    private PlanService planService;

    @Autowired
    private InvoiceService invoiceService;
    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello ADMIN");
    }






}
