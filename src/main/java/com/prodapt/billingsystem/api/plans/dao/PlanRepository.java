package com.prodapt.billingsystem.api.plans.dao;

import com.prodapt.billingsystem.api.plans.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

    public List<Plan> findAll();


    Optional<Plan> findByUuid(UUID subscribedPlanId);
}
