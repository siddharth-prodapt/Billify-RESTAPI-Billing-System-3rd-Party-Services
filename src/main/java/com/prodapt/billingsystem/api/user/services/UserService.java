package com.prodapt.billingsystem.api.user.services;

import com.prodapt.billingsystem.api.plans.dto.PlanRequestDTO;
import com.prodapt.billingsystem.api.user.dto.UserDetailsRequest;

import com.prodapt.billingsystem.api.user.dto.UserMemberRequestDTO;
import com.prodapt.billingsystem.api.user.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService {


    UserDetailsService userDetailsService();
    User addUserDetailsService(Long id, UserDetailsRequest userDetailsRequest);

    User addMemberService(UserMemberRequestDTO member);
    List<User> getAllMembersList(Long parentUserId);

    User subscribePlans(PlanRequestDTO planRequestDTO, Long userId);

}
