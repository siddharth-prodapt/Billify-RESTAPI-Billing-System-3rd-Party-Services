package com.prodapt.billingsystem.api.customer.controller;

import com.prodapt.billingsystem.api.customer.model.Customer;
import com.prodapt.billingsystem.api.customer.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@CrossOrigin
//@RequestMapping("/user")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/customer")
    public String getUserDetails(){
        return "Hii";
    }


  @PostMapping("/customer")
    public ResponseEntity<Customer> addCustomer( @RequestBody Customer customer){
        System.out.println("POST : Customer");
        return this.customerService.addCustomer(customer);
    }

}
