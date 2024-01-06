package com.prodapt.billingsystem.api.user.services;

import com.prodapt.billingsystem.api.invoice.entity.Invoice;
import com.prodapt.billingsystem.api.plans.dto.PlanRequestDTO;
import com.prodapt.billingsystem.api.plans.dto.PlanResponseDTO;
import com.prodapt.billingsystem.api.subscription.entity.dto.SubscriptionRequestDTO;
import com.prodapt.billingsystem.api.subscription.entity.dto.SubscriptionResponseDTO;
import com.prodapt.billingsystem.api.user.dto.*;

import com.prodapt.billingsystem.api.user.entity.MemberAccountEntity;
import com.prodapt.billingsystem.api.user.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UserService {


    UserDetailsService userDetailsService();
    User addUserDetailsService(UUID id, UserDetailsRequest userDetailsRequest);

    User addMemberService(UserMemberRequestDTO member);
    List<User> getAllMembersList(UUID parentUserId);
    SubscriptionResponseDTO subscribePlans(PlanRequestDTO planRequestDTO);

    public List<PlanResponseDTO> getSubscribedPlansList(UUID userUid);

//    List<SubscriptionDetails> getSubscribedPlansList(UUID userUid);

    Invoice paymentOfInvoice(PaymentRequestDTO paymentRequestDTO);

    MemberAccountEntity addMemberAccount(MemberAccountRequestDTO member, UUID uuid);

    List<MemberAccountEntity> getAllMembersAccountList(UUID uuid);

    void subscribePlansForMemberAccount(UUID planUuid, SubscriptionRequestDTO request);

    User getUserDetails(UUID uuid);
}
