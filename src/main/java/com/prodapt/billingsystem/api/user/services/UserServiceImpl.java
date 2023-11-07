package com.prodapt.billingsystem.api.user.services;

import com.prodapt.billingsystem.api.invoice.entity.Invoice;
import com.prodapt.billingsystem.api.invoice.repository.InvoiceRepo;
import com.prodapt.billingsystem.api.plans.dao.PlanRepository;
import com.prodapt.billingsystem.api.plans.dto.PlanRequestDTO;
import com.prodapt.billingsystem.api.plans.dto.PlanResponseDTO;
import com.prodapt.billingsystem.api.plans.entity.Plan;
import com.prodapt.billingsystem.api.subscription.entity.SubscriptionDetails;
import com.prodapt.billingsystem.api.subscription.entity.dao.SubscriptionRepo;
import com.prodapt.billingsystem.api.subscription.entity.dto.SubscriptionResponseDTO;
import com.prodapt.billingsystem.api.user.dto.PaymentRequestDTO;
import com.prodapt.billingsystem.api.user.dto.UserDetailsRequest;
import com.prodapt.billingsystem.api.user.dto.UserDetailsResponse;
import com.prodapt.billingsystem.api.user.dto.UserMemberRequestDTO;
import com.prodapt.billingsystem.api.user.entity.Role;
import com.prodapt.billingsystem.api.user.entity.User;
import com.prodapt.billingsystem.api.user.dao.UserRepository;
import com.prodapt.billingsystem.utility.UtilityMethods;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private SubscriptionRepo subscriptionRepo;

    @Autowired
    private InvoiceRepo invoiceRepo;



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

    public User addMemberService(UserMemberRequestDTO userMemberRequestDTO) {

        User user= userRepository.findByUuid( userMemberRequestDTO.getUserUuid()).orElseThrow(()-> new UsernameNotFoundException("User uuid does not exist"));


        User newMember = new User();

        newMember.setName(userMemberRequestDTO.getName());
        newMember.setRole(Role.ROLE_MEMBER);
        newMember.setParentUser(false);
        newMember.setParentUserId( user.getId());
        newMember.setPhoneNo(userMemberRequestDTO.getPhoneNumber());
        newMember.setAvailable(true);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        user.setCreatedAt(timestamp.toString());
        user.setModifiedAt(timestamp.toString());

        return userRepository.save(newMember);
    }

    @Override
    public List<User> getAllMembersList(UUID userUuid) {
        /* Find user by primary key i.e. id and Role user i.e. parent user */

        User user = userRepository.findByUuid(userUuid).orElseThrow(()-> new UsernameNotFoundException("User not found"));

        Long parentUserId = user.getId();

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

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

//        subs.setCreatedAt(timestamp);
        subs.setUserId(user.getId());
        subs.setPlanId(plan.getId());

        if(plan.getValidityType()=="days")
        {
            subs.setExpiryAt(UtilityMethods.addDays(
                    Timestamp.valueOf(subs.getCreatedAt()), Integer.parseInt(plan.getValidity() )
            ).toString());
        }
        else{
            subs.setExpiryAt(UtilityMethods.addMinutes(
                    Timestamp.valueOf(subs.getCreatedAt()), Integer.parseInt(plan.getValidity() )
            ).toString());

        }


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
        planResponseDTO.setValidityType((plan.getValidityType()));
        planResponseDTO.setPlanType(plan.getPlanType());

        List<PlanResponseDTO> subscribedPlans = List.of(planResponseDTO);

        subscriptionResponseDTO.setSubscribedPlans(subscribedPlans);


        return subscriptionResponseDTO;
    }

    public List<PlanResponseDTO> getSubscribedPlansList(UUID userUid) {
        User user = userRepository.findByUuid(userUid).orElseThrow(() -> new RuntimeException("Invalid id"));

        List<SubscriptionDetails> subsDetails = subscriptionRepo.findAllByUserId(user.getId()).orElseThrow(()->new UsernameNotFoundException("User Not found in subs repo"));
        List<PlanResponseDTO> subscribedPlanList = new ArrayList<>();

        subsDetails
                .forEach(subs -> {
                    Plan plan = planRepository.findById(subs.getPlanId()).orElseThrow(() -> new RuntimeException("Plan not found"));

                    PlanResponseDTO planResponseDTO = new PlanResponseDTO();
                    planResponseDTO.setPlanType(plan.getPlanType());
                    planResponseDTO.setPlanFor(plan.getPlanFor());
                    planResponseDTO.setUuid(plan.getUuid());
                    planResponseDTO.setValidityType(plan.getValidityType());
                    planResponseDTO.setMaxPersons(plan.getMaxPersons());
                    planResponseDTO.setValidity(plan.getValidity());
                    planResponseDTO.setPrice(plan.getPrice());
                    planResponseDTO.setName(plan.getName());
                    planResponseDTO.setImgUrl(plan.getImgUrl());
                    planResponseDTO.setInternet(plan.getInternet());
                    planResponseDTO.setSpeed(plan.getSpeed());

                    subscribedPlanList.add(planResponseDTO);
                });

        return subscribedPlanList;

    }

    public Invoice paymentOfInvoice(PaymentRequestDTO paymentRequestDTO){
        User user = userRepository.findByUuid(paymentRequestDTO.getUserUuid()).orElseThrow(()-> new RuntimeException("Invalid UUID / User not exist"));
        Invoice invoice = invoiceRepo.findByUuid(paymentRequestDTO.getInvoiceUuid() ).orElseThrow(()-> new RuntimeException("Invalid invoice uuid"));

        if(!Objects.equals(invoice.getUserId(), user.getId())){
            throw new RuntimeException("Invoice not associated with this user");
        }


        log.info("Invoice Amount: "+invoice.getAmount()+"req dto:"+paymentRequestDTO.getAmount());

        if(invoice.getAmount() != paymentRequestDTO.getAmount()){
            throw new RuntimeException("Payment Amount does not match");
        }

//        List<PlanResponseDTO> subscribedPlanList = getSubscribedPlansList( user.getUuid());


        invoice.setPaymentStatus(true);
        invoice.setStatus("PAID");
        invoice.setAvailable(false);  // invoice paid successfully therefore paid

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        invoice.setPaymentDate( timestamp.toString());

        return invoiceRepo.save(invoice);

    }
}

