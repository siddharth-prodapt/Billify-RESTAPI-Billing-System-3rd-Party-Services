package com.prodapt.billingsystem.api.user.services;

import com.prodapt.billingsystem.api.plans.dto.PlanRequestDTO;
import com.prodapt.billingsystem.api.plans.dto.PlanResponseDTO;
import com.prodapt.billingsystem.api.subscription.entity.SubscriptionDetails;
import com.prodapt.billingsystem.api.subscription.entity.dto.SubscriptionResponseDTO;
import com.prodapt.billingsystem.api.user.dto.PaymentRequestDTO;
import com.prodapt.billingsystem.api.user.dto.UserDetailsRequest;

import com.prodapt.billingsystem.api.user.dto.UserDetailsResponse;
import com.prodapt.billingsystem.api.user.dto.UserMemberRequestDTO;
import com.prodapt.billingsystem.api.user.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {


    UserDetailsService userDetailsService();
    User addUserDetailsService(Long id, UserDetailsRequest userDetailsRequest);

    User addMemberService(UserMemberRequestDTO member);
    List<User> getAllMembersList(UUID parentUserId);
    SubscriptionResponseDTO subscribePlans(PlanRequestDTO planRequestDTO);

    public List<PlanResponseDTO> getSubscribedPlansList(UUID userUid);

//    List<SubscriptionDetails> getSubscribedPlansList(UUID userUid);

    void paymentOfInvoice(PaymentRequestDTO paymentRequestDTO);
}
