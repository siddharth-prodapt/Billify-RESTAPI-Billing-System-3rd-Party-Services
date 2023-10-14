package com.prodapt.billingsystem.api.user.controller;
import com.prodapt.billingsystem.api.plans.dto.PlanRequestDTO;
import com.prodapt.billingsystem.api.user.dto.UserDetailsRequest;

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


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<String> sayHello() {
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
        return new ResponseEntity<>(newMember, HttpStatus.CREATED);
    }


    /*id: parent user id
     *
     * This function will return list of all associated members */
    @GetMapping("/member/{id}")
    public ResponseEntity<List<UserMemberResponseDTO>> getAllMemberUser(@PathVariable Long id) {

        List<User> membersList = userService.getAllMembersList(id);

        List<UserMemberResponseDTO> responseList = new ArrayList<>();

        membersList
                .forEach(user -> {
                            UserMemberResponseDTO response = new UserMemberResponseDTO();

                            response.setId(user.getId());
                            response.setParentUserId(user.getParentUserId());
                            response.setRole(user.getRole());
                            response.setName(user.getName());
                            response.setCreatedAt(user.getCreatedAt());
                            response.setPhoneNo(user.getPhoneNo());

                            responseList.add(response);
                        }
                );

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }


    @PostMapping("/subscribe/{userId}")
    public ResponseEntity<User> subscribePlan(@RequestBody PlanRequestDTO planRequestDTO, @PathVariable Long userId){
        User user = userService.subscribePlans(planRequestDTO, userId);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}

