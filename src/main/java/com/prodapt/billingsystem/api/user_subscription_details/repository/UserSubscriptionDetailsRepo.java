package com.prodapt.billingsystem.api.user_subscription_details.repository;

import com.prodapt.billingsystem.api.user_subscription_details.entity.UserSubscriptionDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface UserSubscriptionDetailsRepo extends JpaRepository<UserSubscriptionDetails, Long> {

    @Query(value = "select * from user_subscription_details where user_id = ?1",nativeQuery = true)
    List<UserSubscriptionDetails> findAllByUserId(Long userId);


}
