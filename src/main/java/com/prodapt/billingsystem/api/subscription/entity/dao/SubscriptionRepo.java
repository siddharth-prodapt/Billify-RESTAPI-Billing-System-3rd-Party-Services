package com.prodapt.billingsystem.api.subscription.entity.dao;

import com.prodapt.billingsystem.api.subscription.entity.SubscriptionDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepo extends JpaRepository<SubscriptionDetails, Long> {

    List<SubscriptionDetails> findAllByUserId(Long userId);
}
