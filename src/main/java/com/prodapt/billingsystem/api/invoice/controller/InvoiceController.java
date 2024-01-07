package com.prodapt.billingsystem.api.invoice.controller;

import com.prodapt.billingsystem.api.invoice.dto.InvoiceResDTO;
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
@RequestMapping("/api")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private AccountTimerService schedulerService;

    @Autowired
    private UserService userService;

    @PostMapping("/v1/admin/invoice/{uuid}")
    public ResponseEntity<Invoice> generateInvoice(@PathVariable UUID uuid){

        log.info("Generate invoice controller called");
        Invoice invoice = invoiceService.generateInvoiceByUuid(uuid);

        log.info("Scheduler called");
//        schedulerService.checkUserAccountStatus();

        schedulerService.myScheduleFunction();
        return new ResponseEntity<>( invoice, HttpStatus.OK);
    }

    @GetMapping("/v1/user/{uuid}/invoice")
    @ResponseBody
    public ResponseEntity<List<InvoiceResponseDTO>> getUserInvoiceDetailsByUuid(@PathVariable UUID uuid ){
        List<Invoice> invoiceList = invoiceService.getAllUserInvoiceUuid(uuid);
        log.info("INvoice LIst"+invoiceList);
        List<PlanResponseDTO> subscribedPlans = userService.getSubscribedPlansList(uuid);

        List<InvoiceResponseDTO> invoiceResponseDTOList = new ArrayList<>();


        invoiceList.forEach( (invoice)-> {
            InvoiceResponseDTO res = new InvoiceResponseDTO();

            res.setInvoiceUuid( invoice.getUuid());
            res.setEmailId( invoice.getEmailId());
            res.setAmount((float)invoice.getAmount());
            res.setNoOfPlans(invoice.getNosOfPlans());
            res.setPaymentStatus( invoice.isPaymentStatus());
            res.setUserId( uuid.toString());
            res.setSubscribedPlans(subscribedPlans);
            res.setDueDate( invoice.getDueDate() );
            res.setPaymentDate( invoice.getPaymentDate() );

            invoiceResponseDTOList.add(res);
        } );

        return new ResponseEntity<>(invoiceResponseDTOList, HttpStatus.OK);
    }

//    This function will help to generate user invoice using its uuid
    @PostMapping("/v2/admin/invoice/{uuid}")
    public ResponseEntity<InvoiceResDTO> generateUserInvoice(@PathVariable UUID uuid){
        InvoiceResDTO response = invoiceService.generateInvoiceByUserUuid(uuid);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/v2/user/invoice/{uuid}")
    public ResponseEntity<InvoiceResDTO> showInvoiceByUuid(@PathVariable UUID uuid){
        InvoiceResDTO response = invoiceService.getGeneratedInvoiceByUuid(uuid);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/v1/admin/invoice")
    public ResponseEntity<List<InvoiceResDTO>> getInvoice(){
        List<InvoiceResDTO> invoiceList = invoiceService.getInvoices();
        return new ResponseEntity<>(invoiceList, HttpStatus.OK);
    }

}
