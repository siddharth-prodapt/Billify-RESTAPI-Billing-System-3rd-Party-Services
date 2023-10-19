package com.prodapt.billingsystem.api.invoice.controller;

import com.prodapt.billingsystem.api.invoice.dto.InvoiceResponseDTO;
import com.prodapt.billingsystem.api.invoice.entity.Invoice;
import com.prodapt.billingsystem.api.invoice.services.InvoiceService;
import com.prodapt.billingsystem.api.plans.dto.PlanResponseDTO;
import com.prodapt.billingsystem.api.user.services.UserService;
import com.prodapt.billingsystem.common.AccountTimerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private AccountTimerService schedulerService;

    @Autowired
    private UserService userService;

    @PostMapping("/admin/invoice/{uuid}")
    public ResponseEntity<Invoice> generateInvoice(@PathVariable UUID uuid){

        log.info("Generate invoice controller called");
        Invoice invoice = invoiceService.generateInvoiceByUuid(uuid);

        log.info("Scheduler called");
//        schedulerService.checkUserAccountStatus();

        schedulerService.myScheduleFunction();
        return new ResponseEntity<>( invoice, HttpStatus.OK);
    }

    @GetMapping("/user/{uuid}/invoice")
    @ResponseBody
    public ResponseEntity<List<InvoiceResponseDTO>> getUserInvoiceDetailsByUuid(@PathVariable UUID uuid ){
        List<Invoice> invoiceList = invoiceService.getAllUserInvoiceUuid(uuid);

        List<PlanResponseDTO> subscribedPlans = userService.getSubscribedPlansList(uuid);

        List<InvoiceResponseDTO> invoiceResponseDTOList = new ArrayList<>();

        invoiceList.forEach( (invoice)-> {
            InvoiceResponseDTO res = new InvoiceResponseDTO();

            res.setInvoiceUuid( invoice.getUuid());
            res.setEmailId( invoice.getEmailId());
            res.setAmount(invoice.getAmount());
            res.setNoOfPlans(invoice.getNosOfPlans());
            res.setPaymentStatus( invoice.isPaymentStatus());
            res.setUserId( uuid.toString());
            res.setSubscribedPlans(subscribedPlans);

            invoiceResponseDTOList.add(res);
        } );



        return new ResponseEntity<>(invoiceResponseDTOList, HttpStatus.OK);
    }
}
