package com.prodapt.billingsystem.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/email")
public class EmailController {

    @Autowired
    private EmailServices emailServices;

    @PostMapping
    public ResponseEntity<Integer> sendEmail(){
        System.out.println("Send email Controller");
        emailServices.testEmail("sidmail4606@gmail.com");
        return ResponseEntity.ok(200);
    }
}
