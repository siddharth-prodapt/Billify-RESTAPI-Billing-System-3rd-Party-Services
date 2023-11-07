package com.prodapt.billingsystem.email;

import com.prodapt.billingsystem.email.dto.EmailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/api/v1/admin/email")
public class EmailController {

    @Autowired
    private EmailServices emailServices;





    @PostMapping("/test-email")
    public String sendEmailFromCSV(){
        log.info("/test-email hit");
        emailServices.testEmail("sidmail4606@gmail.com");

        return "Email Sent";
    }



}
