package com.prodapt.billingsystem.api.customer.services;

import com.prodapt.billingsystem.api.customer.model.Customer;
import com.prodapt.billingsystem.api.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public ResponseEntity<Customer> addCustomer(Customer customer) {
        Customer newCustomer = new Customer();

        newCustomer.setName(customer.getName());
        newCustomer.setPassword(customer.getPassword());

        customerRepository.save(newCustomer);

        return new ResponseEntity<Customer>(newCustomer,HttpStatus.CREATED);
    }
}
