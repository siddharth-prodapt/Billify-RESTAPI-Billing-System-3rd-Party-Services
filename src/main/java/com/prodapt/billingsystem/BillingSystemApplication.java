package com.prodapt.billingsystem;

import com.prodapt.billingsystem.api.user.dao.UserRepository;
import com.prodapt.billingsystem.api.user.entity.Role;
import com.prodapt.billingsystem.api.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BillingSystemApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    public static void main(String[] args) {
        SpringApplication.run(BillingSystemApplication.class, args);
    }


    public void run(String ...args) {
        User adminAccount = userRepository.findByRole(Role.ADMIN);
        if(adminAccount == null) {
            User user = new User();
            user.setEmail("admin@gmail.com");
            user.setName("ADMIN");
//            user.setFirstname("ADMIN");
//            user.setSecondname("ADMIN");
            user.setRole(Role.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
        }
    }
}



