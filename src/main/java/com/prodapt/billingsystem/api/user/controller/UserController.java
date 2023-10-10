package com.prodapt.billingsystem.api.user.controller;

import com.prodapt.billingsystem.api.user.entity.User;
import com.prodapt.billingsystem.api.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
//@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user){
        return this.userService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginCustomer(@RequestBody User user){
        return this.userService.loginCustomer(user);
    }

}
