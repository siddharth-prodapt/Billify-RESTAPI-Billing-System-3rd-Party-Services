package com.prodapt.billingsystem.api.user.services;

import com.prodapt.billingsystem.api.plans.dao.PlanRepository;
import com.prodapt.billingsystem.api.plans.dto.PlanRequestDTO;
import com.prodapt.billingsystem.api.plans.entity.Plan;
import com.prodapt.billingsystem.api.user.dto.UserDetailsRequest;
import com.prodapt.billingsystem.api.user.dto.UserMemberRequestDTO;
import com.prodapt.billingsystem.api.user.entity.Role;
import com.prodapt.billingsystem.api.user.entity.User;
import com.prodapt.billingsystem.api.user.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlanRepository planRepository;


    public UserDetailsService userDetailsService() {

        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("user not found"));

            }
        };
    }

    public User addUserDetailsService(Long id, UserDetailsRequest userDetailsRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Invalid username"));

        user.setPhoneNo(userDetailsRequest.getPhoneNo());
        user.setDateOfBirth(userDetailsRequest.getDateOfBirth());
        user.setPincode(userDetailsRequest.getPincode());
        user.setCity(userDetailsRequest.getCity());
        user.setState(userDetailsRequest.getState());
        user.setCountry(userDetailsRequest.getCountry());

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        user.setModifiedAt(timestamp.toString());


        return userRepository.save(user);
    }

    public User addMemberService(UserMemberRequestDTO member) {
        User user = new User();

        user.setName(member.getName());
        user.setRole(Role.ROLE_MEMBER);
        user.setParentUser(false);
        user.setParentUserId(member.getParentUserId());
        user.setPhoneNo(member.getPhoneNumber());
        user.setAvailable(true);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        user.setCreatedAt(timestamp.toString());
        user.setModifiedAt(timestamp.toString());

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllMembersList(Long parentUserId) {
        /* Find user by primary key i.e. id and Role user i.e. parent user */
        User parentUser = userRepository.findUserByIdAndRole(parentUserId, Role.ROLE_USER).orElseThrow(() -> new RuntimeException("Parent user id not mapped to any user"));

        /*search database for member users having
         * provided parentUserId and
         * Role is ROLE_MEMBER
         * */
        return userRepository.findUsersByParentUserIdAndRole(parentUser.getId(), Role.ROLE_MEMBER).orElseThrow(() -> new RuntimeException("User Members Not found"));
    }

    public User subscribePlans(PlanRequestDTO planRequestDTO, Long userId){

        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        Plan plan = planRepository.findById(planRequestDTO.getSubscribedPlanId()).orElseThrow(()-> new RuntimeException("Invalid Plan Id"));

        user.getPlans().add(plan);

        if(user == null || plan==null){
            throw new RuntimeException("Either user or email does not exit");
        }

        return userRepository.save(user);
    }
}
