package com.prodapt.billingsystem.api.user.controller;
import com.prodapt.billingsystem.api.plans.dto.PlanRequestDTO;
import com.prodapt.billingsystem.api.plans.dto.PlanResponseDTO;
import com.prodapt.billingsystem.api.plans.entity.Plan;
import com.prodapt.billingsystem.api.subscription.entity.SubscriptionDetails;
import com.prodapt.billingsystem.api.subscription.entity.dto.SubscriptionResponseDTO;
import com.prodapt.billingsystem.api.user.dto.UserDetailsRequest;

import com.prodapt.billingsystem.api.user.dto.UserDetailsResponse;
import com.prodapt.billingsystem.api.user.dto.UserMemberRequestDTO;
import com.prodapt.billingsystem.api.user.dto.UserMemberResponseDTO;
import com.prodapt.billingsystem.api.user.entity.User;
import com.prodapt.billingsystem.api.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<String> sayHello() {
        System.out.println("123123");
        return ResponseEntity.ok("Hello User");
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> addUserDetails(@PathVariable Long id, @RequestBody UserDetailsRequest userDetailsRequest) {
        User user = userService.addUserDetailsService(id, userDetailsRequest);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }


    @PostMapping("/member")
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


    /*id: parent user id
     *
     * This function will return list of all associated members */
    @GetMapping("/{uuid}/member")
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



    @PostMapping("/subscribe")
    public ResponseEntity<SubscriptionResponseDTO> subscribePlan(@RequestBody PlanRequestDTO planRequestDTO ){
        System.out.println("subscribe plan");
        SubscriptionResponseDTO responseDTO = userService.subscribePlans(planRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }



//    User uuid
    @GetMapping("/{uuid}/plans")
    @ResponseBody
    public ResponseEntity<List<PlanResponseDTO>> getSubscribedPlanList(@PathVariable UUID uuid){
        return new ResponseEntity<>( userService.getSubscribedPlansList(uuid), HttpStatus.OK);
    }

    public void payment(){
//        payment: invoice uuid , payment amount
    }




}

