package com.prodapt.billingsystem.api.invoice.services;

import com.prodapt.billingsystem.api.invoice.entity.Invoice;
import com.prodapt.billingsystem.api.invoice.repository.InvoiceRepo;
import com.prodapt.billingsystem.api.plans.dto.PlanResponseDTO;
import com.prodapt.billingsystem.api.subscription.entity.SubscriptionDetails;
import com.prodapt.billingsystem.api.subscription.entity.dao.SubscriptionRepo;
import com.prodapt.billingsystem.api.user.dao.UserRepository;
import com.prodapt.billingsystem.api.user.entity.User;
import com.prodapt.billingsystem.api.user.services.UserService;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepo invoiceRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    EntityManagerFactory emf;

    @Autowired
    SubscriptionRepo subscriptionRepo;

    @Autowired
    UserService userService;
    public Invoice generateInvoiceByUuid(UUID uuid){



        log.info("Generate invoice service");

    User user = userRepository.findByUuid(uuid)
            .orElseThrow(() -> new UsernameNotFoundException("Invalid uuid"));

        List<PlanResponseDTO> subscribedPlanList = userService.getSubscribedPlansList(uuid);

        Invoice newInvoice = new Invoice();

        newInvoice.setEmailId( user.getEmail() );
        newInvoice.setUserId( user.getId());
        newInvoice.setNosOfPlans((int) subscribedPlanList.stream().count());


        float amount = 0;

        for (PlanResponseDTO plan : subscribedPlanList) {
            amount = amount + Float.valueOf(plan.getPrice());
        }


        SubscriptionDetails subs = subscriptionRepo.findByUserId(user.getId())
                .orElseThrow(()-> new RuntimeException("User not subscribed to any Plan"));

        newInvoice.setDueDate(subs.getExpiryAt().toString());

//        subscriptionRepo.findAllByUserId(user.getId()).get( );
//        newInvoice.setDueDate( subscribedPlanList.get(subscribedPlanList.size()-1)  );
        newInvoice.setAmount( amount);


//        InvoiceDB invoice = invoiceRepo.generateInvoice(user.getId())
//                .orElseThrow(()-> new RuntimeException("Invoice details not found"));
//
//        Invoice newInvoice = new Invoice();
//
//        newInvoice.setAmount( invoice.getAmount());
//        newInvoice.setSubsDescId(  invoice.getSubsID( ));
//        newInvoice.setUserId(invoice.getUserId());
//        newInvoice.setNosOfPlans(invoice.getNoOfPlans());

        log.info("Invoice Saved to repo");

        user.setInvoiceGenerated(true);
        userRepository.save(user);

        return  invoiceRepo.save(newInvoice);
    }


//    Return List of invoices related to particular user
    public List<Invoice>  getAllUserInvoiceUuid(UUID uuid){
//        User user = userRepository.findByUuidAndAvailableIsTrue(uuid).orElseThrow(()-> new UsernameNotFoundException("User not found"));

        User user = userRepository.findByUuid(uuid).orElseThrow(()-> new UsernameNotFoundException("User not found"));

        List<Invoice> invoiceList = invoiceRepo.findAllByUserId(user.getId());
        return invoiceList;
    }

    public Invoice getInvoiceByUuid(UUID uuid){
        Invoice invoice = invoiceRepo.findByUuid(uuid).orElseThrow(()-> new RuntimeException("Invalid invoice uuid"));
        return invoice;
    }


}
