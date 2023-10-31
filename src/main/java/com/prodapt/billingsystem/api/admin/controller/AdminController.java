package com.prodapt.billingsystem.api.admin.controller;

import com.prodapt.billingsystem.api.admin.dto.AdminDashboardResponseDTO;
import com.prodapt.billingsystem.api.admin.services.AdminService;
import com.prodapt.billingsystem.api.invoice.entity.Invoice;
import com.prodapt.billingsystem.api.invoice.services.InvoiceService;
import com.prodapt.billingsystem.api.plans.dto.PlanResponseDTO;
import com.prodapt.billingsystem.api.plans.services.PlanService;
import com.prodapt.billingsystem.api.user.dto.UserResponseDTO;
import com.prodapt.billingsystem.api.user.services.UserService;
import com.prodapt.billingsystem.common.AccountTimerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private UserService userService;
    private PlanService planService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AccountTimerService accountTimerService;

    @Autowired
    private InvoiceService invoiceService;
    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello ADMIN");
    }

    @PostMapping("/stop-scheduler")
    public String stopScheduler(){

        accountTimerService.stopScheduledTask();

        return "Scheduler Stopped Successfully";

    }

    @PostMapping("/start-scheduler")
    public String startScheduler(){
        accountTimerService.startScheduledTask();
        return "Scheduler started successfully";
    }


    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardResponseDTO> getDashboardDetails(){
        return new ResponseEntity<>(adminService.getDashboardDetails(), HttpStatus.OK);
    }

    @GetMapping("/user")
    @ResponseBody
    public ResponseEntity<List<UserResponseDTO>> getAllUserList(){
        return new ResponseEntity<>(adminService.getAllUsersList() , HttpStatus.OK);
    }

    @GetMapping("/user/{uuid}/plans")
    public ResponseEntity<List<PlanResponseDTO>> getUserSubscribedPlansByUuid(@PathVariable UUID uuid){
      return new ResponseEntity<>( adminService.getSubscribedPlanList(uuid), HttpStatus.OK) ;
    }

    @CrossOrigin
    @PostMapping("/toggle-account/user/{uuid}")
    public ResponseEntity<UserResponseDTO> toggleUserAccountAccess(@PathVariable UUID uuid){
        System.out.println("toggler called");
        return new ResponseEntity<>(adminService.toggleAccountAccessService(uuid), HttpStatus.OK);
    }
}
