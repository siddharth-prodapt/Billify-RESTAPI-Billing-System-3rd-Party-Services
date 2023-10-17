package com.prodapt.billingsystem.api.invoice.services;

import com.prodapt.billingsystem.api.invoice.dto.InvoiceDB;
import com.prodapt.billingsystem.api.invoice.entity.Invoice;
import com.prodapt.billingsystem.api.invoice.repository.InvoiceRepo;
import com.prodapt.billingsystem.api.user.dao.UserRepository;
import com.prodapt.billingsystem.api.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepo invoiceRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    EntityManagerFactory emf;
    public Invoice generateInvoiceByUuid(UUID uuid){
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(()-> new UsernameNotFoundException("Invalid uuid"));

        InvoiceDB invoice = invoiceRepo.generateInvoice(user.getId())
                .orElseThrow(()-> new RuntimeException("Invoice details not found"));

        Invoice newInvoice = new Invoice();

        newInvoice.setAmount( invoice.getAmount());
        newInvoice.setSubsDescId(  invoice.getSubsID( ));
        newInvoice.setUserId(invoice.getUserId());
        newInvoice.setNosOfPlans(invoice.getNoOfPlans());


        return  invoiceRepo.save(newInvoice);
    }

    public Invoice  getAllInvoiceByUserUuid(UUID uuid){
        User user = userRepository.findByUuid(uuid).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        invoiceRepo.findAllByUserId(user.getId());
        return null;
    }

    public Invoice getInvoiceByUuid(UUID uuid){
        Invoice invoice = invoiceRepo.findByUuid(uuid).orElseThrow(()-> new RuntimeException("Invalid invoice uuid"));
        return invoice;
    }


}
