package com.prodapt.billingsystem.api.user.controller;
import com.prodapt.billingsystem.api.invoice.entity.Invoice;
import com.prodapt.billingsystem.api.plans.dto.PlanRequestDTO;
import com.prodapt.billingsystem.api.plans.dto.PlanResponseDTO;
import com.prodapt.billingsystem.api.plans.entity.Plan;
import com.prodapt.billingsystem.api.subscription.entity.SubscriptionDetails;
import com.prodapt.billingsystem.api.subscription.entity.dto.SubscriptionResponseDTO;
import com.prodapt.billingsystem.api.user.dto.*;

import com.prodapt.billingsystem.api.user.entity.MemberAccountEntity;
import com.prodapt.billingsystem.api.user.entity.User;
import com.prodapt.billingsystem.api.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @GetMapping("/v1/user")
    public ResponseEntity<String> sayHello() {
        System.out.println("123123");
        return ResponseEntity.ok("Hello User");
    }

    @PutMapping("/v1/user/{id}")
    public ResponseEntity<User> addUserDetails(@PathVariable Long id, @RequestBody UserDetailsRequest userDetailsRequest) {
        User user = userService.addUserDetailsService(id, userDetailsRequest);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }


    @PostMapping("/v1/user/member")
    public ResponseEntity<User> addMember(@RequestBody UserMemberRequestDTO member) {
        User newMember = userService.addMemberService(member);

        UserMemberResponseDTO responseDTO = new UserMemberResponseDTO();

        responseDTO.setMemberUuid((newMember.getUuid()));
        responseDTO.setName(newMember.getName());
        responseDTO.setPhoneNo(newMember.getPhoneNo());
        responseDTO.setRole(newMember.getRole());
        responseDTO.setCreatedAt(newMember.getCreatedAt());

        return new ResponseEntity<>(newMember, HttpStatus.CREATED);
    }

/*To add Account under user
* Archtecture of Member Account
* User--->Account---->Plans/subscriptions
* */
    @PostMapping("/v2/user/{uuid}/member")
    public ResponseEntity<MemberAccountEntity> addMemberAccount(@RequestBody MemberAccountRequestDTO member,
                                                                @PathVariable UUID uuid){
        MemberAccountEntity newMemberAccount = userService.addMemberAccount(member, uuid);
        return new ResponseEntity<>(newMemberAccount, HttpStatus.CREATED);
    }

/*
* Return List of Member Account
* */
    @GetMapping("/v2/user/{uuid}/member")
    public ResponseEntity<List<MemberAccountEntity>> getAllMemberAccountByUserUuid(@PathVariable UUID uuid){
        List<MemberAccountEntity> memberAccountsList = userService.getAllMembersAccountList(uuid);

        return new ResponseEntity<>(memberAccountsList, HttpStatus.OK);
    }


    /*
     * id: parent user id
     * This function will return list of all associated members
     */
    @GetMapping("/v1/user/{uuid}/member")
    @ResponseBody
    public ResponseEntity<List<UserMemberResponseDTO>> getAllMemberByUserUuid(@PathVariable UUID uuid) {

        List<User> membersList = userService.getAllMembersList(uuid);

        List<UserMemberResponseDTO> responseList = new ArrayList<>();

        membersList
                .forEach(user -> {
                            UserMemberResponseDTO response = new UserMemberResponseDTO();

                            response.setMemberUuid(user.getUuid());
                            response.setRole(user.getRole());
                            response.setName(user.getName());
                            response.setCreatedAt(user.getCreatedAt());
                            response.setPhoneNo(user.getPhoneNo());

                            responseList.add(response);
                        }
                );

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }



    @PostMapping("/v1/user/subscribe")
    public ResponseEntity<SubscriptionResponseDTO> subscribePlan(@RequestBody PlanRequestDTO planRequestDTO ){
        System.out.println("subscribe plan");
        SubscriptionResponseDTO responseDTO = userService.subscribePlans(planRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }



//    User uuid
    @GetMapping("/v1/user/{uuid}/plans")
    @ResponseBody
    public ResponseEntity<List<PlanResponseDTO>> getSubscribedPlanList(@PathVariable UUID uuid){
        return new ResponseEntity<>( userService.getSubscribedPlansList(uuid), HttpStatus.OK);
    }

    @PostMapping("/v1/user/invoice-payment")
    public ResponseEntity<Invoice> payment(@RequestBody PaymentRequestDTO paymentRequestDTO){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        paymentRequestDTO.setPaymentTime(timestamp.toString());

        return new ResponseEntity<Invoice>(userService.paymentOfInvoice(paymentRequestDTO), HttpStatus.OK);
    }



}

