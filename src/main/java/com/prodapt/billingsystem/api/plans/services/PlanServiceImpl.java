package com.prodapt.billingsystem.api.plans.services;

import com.prodapt.billingsystem.api.plans.dao.PlanRepository;
import com.prodapt.billingsystem.api.plans.entity.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanRepository planRepository;
    @Override
    public Plan createPlan(Plan plan){
        Plan newPlan = new Plan();

        newPlan.setName(plan.getName());
        newPlan.setPlanFor(plan.getPlanFor());
        newPlan.setPrice(plan.getPrice());
        newPlan.setMaxPersons(plan.getMaxPersons());

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        newPlan.setCreatedAt( timestamp.toString());
        newPlan.setModifiedAt(timestamp.toString());
        newPlan.setAvailable(true);
        newPlan.setValidity(plan.getValidity());
        newPlan.setDurationType(plan.getDurationType());
        newPlan.setPlanType(plan.getPlanType());

        return planRepository.save(newPlan);
    }

    @Override
    public List<Plan> getAllPlanList() {
        return planRepository.findAll();
    }
}
