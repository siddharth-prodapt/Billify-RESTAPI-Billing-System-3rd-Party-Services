package com.prodapt.billingsystem.api.customer.services;

import com.prodapt.billingsystem.api.customer.model.Customer;
import com.prodapt.billingsystem.api.customer.repository.CustomerRepository;
import org.springframework.http.ResponseEntity;

public interface CustomerService{

    public ResponseEntity<Customer> addCustomer(Customer customer);
}
