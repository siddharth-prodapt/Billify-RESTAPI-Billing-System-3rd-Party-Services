package com.prodapt.billingsystem.api.user.services;

import com.prodapt.billingsystem.api.user.entity.User;
import com.prodapt.billingsystem.api.user.dao.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private CustomerRepository userRepository;
    @Override
    public ResponseEntity<User> registerUser(User user) {
        User newUser = new User();

        newUser.setName(user.getName());
        newUser.setPassword(user.getPassword());
        newUser.setPhoneNo(user.getPhoneNo());
        newUser.setEmail(user.getEmail());
        newUser.setDateOfBirth(user.getDateOfBirth());
        newUser.setPincode(user.getPincode());
        newUser.setCity(user.getCity());
        newUser.setState(user.getState());
        newUser.setCountry(user.getCountry());
        newUser.setParentUser( user.isParentUser());

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        newUser.setCreatedAt(timestamp.toString());
        newUser.setModifiedAt(timestamp.toString());

        if(user.isParentUser()){
            newUser.setRole("PARENT_USER");
        }else{
            newUser.setRole("USER");
        }

        newUser.setAvailable(true);

        userRepository.save(newUser);

        return new ResponseEntity<User>(newUser,HttpStatus.OK);
    }

    public boolean isUserRegistered(User user){
       User user1 =  this.userRepository.findCustomerByNameAndPassword(user.getName(), user.getPassword());

       System.out.println("Is Customer Registered: ");
       System.out.println(user1.getName());

        return user1 != null;
    }
    public ResponseEntity<User> loginCustomer (User user){
        if(isUserRegistered(user)) {
            User cust = userRepository.findCustomerByNameAndPassword(user.getName(), user.getPassword());
            System.out.println(cust.toString());
            return new ResponseEntity<User>(cust, HttpStatus.OK);
        }
//        return new ResponseEntity<Customer>(customerRepository.findCustomerByName(customer.getName()), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findUserById(id) ;
    }
}
