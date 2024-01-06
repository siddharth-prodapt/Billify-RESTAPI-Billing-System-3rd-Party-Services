package com.prodapt.billingsystem.api.membersubscription.repository;

import com.prodapt.billingsystem.api.membersubscription.entity.MemberSubscriptionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberSubscriptionDetailsRepo extends JpaRepository<MemberSubscriptionDetails, Long> {
}
