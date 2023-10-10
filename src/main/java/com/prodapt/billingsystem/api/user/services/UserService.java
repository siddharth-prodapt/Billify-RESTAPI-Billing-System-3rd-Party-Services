package com.prodapt.billingsystem.api.user.services;

import com.prodapt.billingsystem.api.user.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    public ResponseEntity<User> registerUser(User user);
    public ResponseEntity<User> loginCustomer(User user);
    public User findUserById(Long id);
}
