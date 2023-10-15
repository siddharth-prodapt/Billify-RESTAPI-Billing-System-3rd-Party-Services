package com.prodapt.billingsystem.api.user.services;

import com.prodapt.billingsystem.api.plans.dao.PlanRepository;
import com.prodapt.billingsystem.api.plans.dto.PlanRequestDTO;
import com.prodapt.billingsystem.api.plans.dto.PlanResponseDTO;
import com.prodapt.billingsystem.api.plans.entity.Plan;
import com.prodapt.billingsystem.api.subscription.entity.SubscriptionDetails;
import com.prodapt.billingsystem.api.subscription.entity.dao.SubscriptionRepo;
import com.prodapt.billingsystem.api.subscription.entity.dto.SubscriptionResponseDTO;
import com.prodapt.billingsystem.api.user.dto.UserDetailsRequest;
import com.prodapt.billingsystem.api.user.dto.UserDetailsResponse;
import com.prodapt.billingsystem.api.user.dto.UserMemberRequestDTO;
import com.prodapt.billingsystem.api.user.entity.Role;
import com.prodapt.billingsystem.api.user.entity.User;
import com.prodapt.billingsystem.api.user.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private SubscriptionRepo subscriptionRepo;


    public UserDetailsService userDetailsService() {

        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("user not found"));

            }
        };
    }

    public User addUserDetailsService(Long id, UserDetailsRequest userDetailsRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Invalid username"));

        user.setPhoneNo(userDetailsRequest.getPhoneNo());
        user.setDateOfBirth(userDetailsRequest.getDateOfBirth());
        user.setPincode(userDetailsRequest.getPincode());
        user.setCity(userDetailsRequest.getCity());
        user.setState(userDetailsRequest.getState());
        user.setCountry(userDetailsRequest.getCountry());

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        user.setModifiedAt(timestamp.toString());


        return userRepository.save(user);
    }

    public User addMemberService(UserMemberRequestDTO member) {
        User user = new User();

        user.setName(member.getName());
        user.setRole(Role.ROLE_MEMBER);
        user.setParentUser(false);
        user.setParentUserId(member.getParentUserId());
        user.setPhoneNo(member.getPhoneNumber());
        user.setAvailable(true);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        user.setCreatedAt(timestamp.toString());
        user.setModifiedAt(timestamp.toString());

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllMembersList(Long parentUserId) {
        /* Find user by primary key i.e. id and Role user i.e. parent user */
        User parentUser = userRepository.findUserByIdAndRole(parentUserId, Role.ROLE_USER).orElseThrow(() -> new RuntimeException("Parent user id not mapped to any user"));

        /*search database for member users having
         * provided parentUserId and
         * Role is ROLE_MEMBER
         * */
        return userRepository.findUsersByParentUserIdAndRole(parentUser.getId(), Role.ROLE_MEMBER).orElseThrow(() -> new RuntimeException("User Members Not found"));
    }


    public SubscriptionResponseDTO subscribePlans(PlanRequestDTO planRequestDTO) {

        User user = userRepository.findByUuid(planRequestDTO.getId()).orElseThrow(() -> new RuntimeException("User does not exist"));

        Plan plan = planRepository.findByUuid(planRequestDTO.getSubscribedPlanId()).orElseThrow(() -> new RuntimeException("Plan does not exist"));

        SubscriptionDetails subs = new SubscriptionDetails();
        subs.setUserId(user.getId());
        subs.setPlanId(plan.getId());

        subs.setActive(true);

//        subs.setExpiryAt();  // activation date + duration of days


        subscriptionRepo.save(subs);

        SubscriptionResponseDTO subscriptionResponseDTO = new SubscriptionResponseDTO();

        subscriptionResponseDTO.setUserUuid(user.getUuid());
        subscriptionResponseDTO.setEmail(user.getEmail());

        PlanResponseDTO planResponseDTO = new PlanResponseDTO();

        planResponseDTO.setUuid(plan.getUuid());
        planResponseDTO.setName(plan.getName());
        planResponseDTO.setPlanFor(plan.getPlanFor());
        planResponseDTO.setPrice(plan.getPrice());
        planResponseDTO.setMaxPersons(plan.getMaxPersons());
        planResponseDTO.setValidity(plan.getValidity());
        planResponseDTO.setDurationType(plan.getDurationType());
        planResponseDTO.setPlanType(plan.getPlanType());

        List<PlanResponseDTO> subscribedPlans = List.of(planResponseDTO);

        subscriptionResponseDTO.setSubscribedPlans(subscribedPlans);


        return subscriptionResponseDTO;
    }

    public List<PlanResponseDTO> getSubscribedPlansList(UUID userUid) {
        User user = userRepository.findByUuid(userUid).orElseThrow(() -> new RuntimeException("Invalid id"));
        List<SubscriptionDetails> subsDetails = subscriptionRepo.findAllByUserId(user.getId());
        List<PlanResponseDTO> subscribedPlanList = new ArrayList<>();

        subsDetails
                .forEach(subs -> {
                    Plan plan = planRepository.findById(subs.getPlanId()).orElseThrow(() -> new RuntimeException("Plan not found"));

                    PlanResponseDTO planResponseDTO = new PlanResponseDTO();
                    planResponseDTO.setPlanType(plan.getPlanType());
                    planResponseDTO.setPlanFor(plan.getPlanFor());
                    planResponseDTO.setUuid(plan.getUuid());
                    planResponseDTO.setDurationType(plan.getDurationType());
                    planResponseDTO.setMaxPersons(plan.getMaxPersons());
                    planResponseDTO.setValidity(plan.getValidity());
                    planResponseDTO.setPrice(plan.getPrice());
                    planResponseDTO.setName(plan.getName());

                    subscribedPlanList.add(planResponseDTO);
                });

        return subscribedPlanList;

    }

}

