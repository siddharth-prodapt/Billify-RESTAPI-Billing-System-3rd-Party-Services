package com.prodapt.billingsystem.api.plans.services;

import com.prodapt.billingsystem.api.plans.entity.Plan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface PlanService {
    public Plan createPlan(Plan plan);
    public List<Plan> getAllPlanList();

//    List<Plan> getSubscribedPlanList(Long id);

}
