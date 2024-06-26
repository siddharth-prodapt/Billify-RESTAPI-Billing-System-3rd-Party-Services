package com.prodapt.billingsystem.api.plans.controller;

import com.prodapt.billingsystem.api.plans.entity.Plan;
import com.prodapt.billingsystem.api.plans.services.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class PlanController {
    @Autowired
    private PlanService planService;

    @PostMapping("/admin/plans")
    public ResponseEntity<Plan> createPlans(@RequestBody Plan plan){
        return new ResponseEntity<>(planService.createPlan(plan), HttpStatus.CREATED);
    }

    @GetMapping("/user/plans")
    public ResponseEntity<List<Plan>> getAllPlan(){
        return new ResponseEntity<>(planService.getAllPlanList(), HttpStatus.OK);
    }

    /*
    @GetMapping("/user/plans/{id}")
    public ResponseEntity<List<Plan>> getSubscribedPlanDetails(@PathVariable Long id){
        return new ResponseEntity<>(planService.getSubscribedPlanList(id), HttpStatus.OK);
    }

     */

    public void getPlans(){}

}
