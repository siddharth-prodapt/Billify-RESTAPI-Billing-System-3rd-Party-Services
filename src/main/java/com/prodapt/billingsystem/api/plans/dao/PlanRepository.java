package com.prodapt.billingsystem.api.plans.dao;

import com.prodapt.billingsystem.api.plans.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

    public List<Plan> findAll();


}
