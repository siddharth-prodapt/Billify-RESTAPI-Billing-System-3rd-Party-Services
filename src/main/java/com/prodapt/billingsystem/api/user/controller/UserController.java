package com.prodapt.billingsystem.api.user.controller;

import com.prodapt.billingsystem.api.user.dto.UserDetailsRequest;
import com.prodapt.billingsystem.api.user.entity.User;
import com.prodapt.billingsystem.api.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello User");
    }

    @PutMapping ("/{id}")
    public ResponseEntity<User> addUserDetails(@PathVariable Long id, @RequestBody UserDetailsRequest userDetailsRequest){
       User user  =  userService.addUserDetailsService(id, userDetailsRequest);
       return new ResponseEntity<User>(user, HttpStatus.OK);
    }


}

