package com.prodapt.billingsystem.api.plans.services;

import com.prodapt.billingsystem.api.plans.entity.Plan;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PlanService {
    public Plan createPlan(Plan plan);
    public List<Plan> getAllPlanList();
}
