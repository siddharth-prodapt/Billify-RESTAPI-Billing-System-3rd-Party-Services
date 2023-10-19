package com.prodapt.billingsystem.api.subscription.entity.dao;

import com.prodapt.billingsystem.api.subscription.entity.SubscriptionDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepo extends JpaRepository<SubscriptionDetails, Long> {

    Optional<List<SubscriptionDetails>> findAllByUserId(Long userId);
    Optional<SubscriptionDetails> findByUserId(Long userId);
}
