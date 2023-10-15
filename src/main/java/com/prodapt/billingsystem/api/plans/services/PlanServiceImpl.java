package com.prodapt.billingsystem.api.plans.services;

import com.prodapt.billingsystem.api.plans.dao.PlanRepository;
import com.prodapt.billingsystem.api.plans.entity.Plan;
import com.prodapt.billingsystem.api.user.dao.UserRepository;
import com.prodapt.billingsystem.api.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private UserRepository userRepository;
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

    /*
    @Override
    public List<Plan> getSubscribedPlanList(Long id) {
        boolean exist = userRepository.existsById(id);

        if(!exist)
            throw new RuntimeException("User id does not exist");

        User user = userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("User id not registered"));
//        return user.getSubscribedPlans();
    }
     */


}
