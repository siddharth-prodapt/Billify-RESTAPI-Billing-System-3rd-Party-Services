package com.prodapt.billingsystem.api.user.services;

import com.prodapt.billingsystem.api.user.dto.UserDetailsRequest;
import com.prodapt.billingsystem.api.user.entity.User;
import com.prodapt.billingsystem.api.user.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Timestamp;

/*
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
//    @Override
//    public ResponseEntity<User> registerUser(User user) {
//        User newUser = new User();
//
//        newUser.setName(user.getName());
//        newUser.setPassword(user.getPassword());
//        newUser.setPhoneNo(user.getPhoneNo());
//        newUser.setEmail(user.getEmail());
//        newUser.setDateOfBirth(user.getDateOfBirth());
//        newUser.setPincode(user.getPincode());
//        newUser.setCity(user.getCity());
//        newUser.setState(user.getState());
//        newUser.setCountry(user.getCountry());
//        newUser.setParentUser( user.isParentUser());
//
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//
//        newUser.setCreatedAt(timestamp.toString());
//        newUser.setModifiedAt(timestamp.toString());
//
//        if(user.isParentUser()){
//            newUser.setRole("PARENT_USER");
//        }else{
//            newUser.setRole("USER");
//        }
//
//        newUser.setAvailable(true);
//
//        userRepository.save(newUser);
//
//        return new ResponseEntity<User>(newUser,HttpStatus.OK);
//    }

//    public boolean isUserRegistered(User user){
//       User user1 =  this.userRepository.findCustomerByNameAndPassword(user.getName(), user.getPassword());
//
//       System.out.println("Is Customer Registered: ");
//       System.out.println(user1.getName());
//
//        return user1 != null;
//    }
//    public ResponseEntity<User> loginCustomer (User user){
//        if(isUserRegistered(user)) {
//            User cust = userRepository.findCustomerByNameAndPassword(user.getName(), user.getPassword());
//            System.out.println(cust.toString());
//            return new ResponseEntity<User>(cust, HttpStatus.OK);
//        }
////        return new ResponseEntity<Customer>(customerRepository.findCustomerByName(customer.getName()), HttpStatus.OK);
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @Override
    public ResponseEntity<User> registerUser(User user) {
        return null;
    }

    @Override
    public ResponseEntity<User> loginCustomer(User user) {
        return null;
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findUserById(id) ;
    }

    @Override
    public UserDetailsService userDetailsService() {
        return null;
    }
}
*/

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    public UserDetailsService userDetailsService() {

        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("user not found"));

            }
        };
    }

    public User addUserDetailsService(Long id, UserDetailsRequest userDetailsRequest){
        User user = userRepository.findById(id).orElseThrow(()->new UsernameNotFoundException("Invalid username"));

        user.setPhoneNo(userDetailsRequest.getPhoneNo());
        user.setDateOfBirth(userDetailsRequest.getDateOfBirth());
        user.setPincode(userDetailsRequest.getPincode());
        user.setCity(userDetailsRequest.getCity());
        user.setState(userDetailsRequest.getState());
        user.setCountry(userDetailsRequest.getCountry());

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        user.setModifiedAt( timestamp.toString() );


        return userRepository.save(user);
    }
}
