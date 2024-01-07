package com.prodapt.billingsystem.api.invoice.services;

import com.prodapt.billingsystem.api.invoice.dto.InvoiceResDTO;
import com.prodapt.billingsystem.api.invoice.dto.InvoiceResponseDTO;
import com.prodapt.billingsystem.api.invoice.entity.Invoice;
import com.prodapt.billingsystem.api.invoice.repository.InvoiceRepo;
import com.prodapt.billingsystem.api.plans.dao.PlanRepository;
import com.prodapt.billingsystem.api.plans.dto.InvoicePlanDetailsDTO;
import com.prodapt.billingsystem.api.plans.dto.PlanResponseDTO;
import com.prodapt.billingsystem.api.subscription.entity.SubscriptionDetails;
import com.prodapt.billingsystem.api.subscription.entity.dao.SubscriptionRepo;
import com.prodapt.billingsystem.api.user.dao.UserRepository;
import com.prodapt.billingsystem.api.user.entity.User;
import com.prodapt.billingsystem.api.user.services.UserService;
import com.prodapt.billingsystem.api.user_subscription_details.entity.UserSubscriptionDetails;
import com.prodapt.billingsystem.api.user_subscription_details.repository.UserSubscriptionDetailsRepo;
import com.prodapt.billingsystem.utility.UtilityMethods;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
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
    private PlanRepository planRepository;

    @Autowired
    private UserSubscriptionDetailsRepo userSubscriptionDetailsRepo;

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
        newInvoice.setAmount( (int)amount);


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

    @Override
    public InvoiceResDTO generateInvoiceByUserUuid(UUID uuid) {

        InvoiceResDTO invoiceResponse = new InvoiceResDTO();
        List<InvoicePlanDetailsDTO> planDetailsList = new ArrayList<>();

        User user = userRepository.findByUuid(uuid).get();
        Long userId = user.getId();

        List<UserSubscriptionDetails> subscriptionDetails = userSubscriptionDetailsRepo.findAllByUserId(userId);

        List<Long> subsPlansIdList = new ArrayList<>();
        List<BigDecimal> subsPlansRateList = new ArrayList<>();
        float sumTotal = 0;

        for(UserSubscriptionDetails subs : subscriptionDetails){

            InvoicePlanDetailsDTO plan = new InvoicePlanDetailsDTO();

            plan.setPlanName( planRepository.findById(subs.getPlanId()).get().getName() );
            plan.setPlanAmount( planRepository.findById(subs.getPlanId()).get().getPrice() );
            plan.setRatePerDay(subs.getRatePerDay()+"");
            plan.setActivationDate( subs.getCreatedAt() );

            planDetailsList.add(plan);

            subsPlansIdList.add(subs.getPlanId());
            subsPlansRateList.add(subs.getRatePerDay());

            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            long days = UtilityMethods.nosOfDays(currentTimestamp, Timestamp.valueOf(subs.getBillingDate()));

            if(days==0)
                days=1;
            //nosOfDays*rate
            sumTotal= sumTotal + (Float.valueOf(String.valueOf(subs.getRatePerDay())) * days);

        }

        int amount = (int) sumTotal;

        Invoice invoice = new Invoice();
        invoice.setUserId(userId);
        invoice.setSubscribedPlansId(subsPlansIdList);
        invoice.setStatus("PENDING");
        invoice.setPaymentStatus(false);

        String timestamp = String.valueOf(new Timestamp( System.currentTimeMillis()));

        invoice.setCreatedAt(timestamp);
        invoice.setDueDate(String.valueOf(UtilityMethods.addDays(Timestamp.valueOf(timestamp), 10)));

        invoice.setSumTotal(sumTotal);
        invoice.setAmount(amount);
        log.info("Subscribed Plan Id added to list");

        invoiceRepo.save(invoice);

        invoiceResponse.setInvoiceId(invoice.getId());
        invoiceResponse.setInvoiceUuid(invoice.getUuid());
        invoiceResponse.setAmount(invoice.getAmount()+"");
        invoiceResponse.setEmail(user.getEmail());
        invoiceResponse.setName(user.getName());
        invoiceResponse.setSumTotal(invoice.getSumTotal()+"");
        invoiceResponse.setBillDate(invoice.getCreatedAt());
        invoiceResponse.setDiscount("0%");
        invoiceResponse.setBillDueDate(invoice.getDueDate());
        invoiceResponse.setSubscribedPlanDetails( planDetailsList);

        log.info("Invoice generated successfully");

        return invoiceResponse;
    }

    @Override
    public InvoiceResDTO getGeneratedInvoiceByUuid(UUID uuid) {
       return invoiceToResponseDTO(uuid);
    }

    @Override
    public List<InvoiceResDTO> getInvoices() {

        log.info("Total invoices generated: "+invoiceRepo.count());

        List<Invoice> invoiceList = invoiceRepo.findAll();
        List<InvoiceResDTO> invoiceResList = new ArrayList<>();

        for(Invoice i : invoiceList){
            UUID uuid = i.getUuid();
            invoiceResList.add(invoiceToResponseDTO(uuid));
        }
        log.info("Invoice Response List: "+invoiceResList);

        return invoiceResList;
    }

    public Invoice getInvoiceByUuid(UUID uuid){
        Invoice invoice = invoiceRepo.findByUuid(uuid)
                .orElseThrow(()-> new RuntimeException("Invalid invoice uuid"));
        return invoice;
    }


    //utility method to change invoice into proper response DTO
    public InvoiceResDTO invoiceToResponseDTO(UUID invoiceUuid){

        Invoice invoice = invoiceRepo.findByUuid(invoiceUuid)
                .orElseThrow(()-> new RuntimeException("Invoice Not generated yet"));

        List<InvoicePlanDetailsDTO> planDetailsList = new ArrayList<>();
        List<UserSubscriptionDetails> subscriptionDetails = userSubscriptionDetailsRepo.findAllByUserId(invoice.getUserId());

        for(UserSubscriptionDetails subs : subscriptionDetails){
            InvoicePlanDetailsDTO plan = new InvoicePlanDetailsDTO();

            plan.setActivationDate(subs.getBillingDate());
            plan.setPlanName(planRepository.findById(subs.getPlanId()).get().getName());
            plan.setPlanAmount(planRepository.findById(subs.getPlanId()).get().getPrice());
            plan.setRatePerDay(String.valueOf(subs.getRatePerDay()));

            planDetailsList.add(plan);
        }
        InvoiceResDTO invoiceResponse = new InvoiceResDTO();
        invoiceResponse.setInvoiceId(invoice.getId());
        invoiceResponse.setInvoiceUuid(invoice.getUuid());
        invoiceResponse.setAmount(invoice.getAmount()+"");
        invoiceResponse.setEmail(invoice.getEmailId());
        invoiceResponse.setName( userRepository.findById(invoice.getUserId()).get().getName() );
        invoiceResponse.setSumTotal(invoice.getSumTotal()+"");
        invoiceResponse.setBillDate(invoice.getCreatedAt());
        invoiceResponse.setDiscount("0%");
        invoiceResponse.setBillDueDate(invoice.getDueDate());
        invoiceResponse.setSubscribedPlanDetails( planDetailsList);

        return invoiceResponse;
    }


}
