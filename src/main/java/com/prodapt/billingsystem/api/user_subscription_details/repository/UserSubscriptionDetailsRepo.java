package com.prodapt.billingsystem.api.user_subscription_details.repository;

import com.prodapt.billingsystem.api.user_subscription_details.entity.UserSubscriptionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSubscriptionDetailsRepo extends JpaRepository<UserSubscriptionDetails, Long> {

}
