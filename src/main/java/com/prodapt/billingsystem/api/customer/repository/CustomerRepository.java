package com.prodapt.billingsystem.api.customer.repository;

import com.prodapt.billingsystem.api.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
