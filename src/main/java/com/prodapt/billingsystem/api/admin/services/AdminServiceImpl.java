package com.prodapt.billingsystem.api.admin.services;

import com.prodapt.billingsystem.api.admin.dto.AdminDashboardResponseDTO;
import com.prodapt.billingsystem.api.invoice.repository.InvoiceRepo;
import com.prodapt.billingsystem.api.plans.dao.PlanRepository;
import com.prodapt.billingsystem.api.plans.dto.PlanResponseDTO;
import com.prodapt.billingsystem.api.subscription.entity.dao.SubscriptionRepo;
import com.prodapt.billingsystem.api.user.dao.UserRepository;
import com.prodapt.billingsystem.api.user.dto.UserResponseDTO;
import com.prodapt.billingsystem.api.user.entity.Role;
import com.prodapt.billingsystem.api.user.entity.User;
import com.prodapt.billingsystem.api.user.services.UserService;
import com.prodapt.billingsystem.api.user_subscription_details.repository.UserSubscriptionDetailsRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.util.Uu;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private InvoiceRepo invoiceRepo;

    @Autowired
    private SubscriptionRepo subscriptionRepo;

    @Autowired
    private UserSubscriptionDetailsRepo userSubscriptionDetailsRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    @Override
    public List<UserResponseDTO> getAllUsersList() {
        List<User> userList = userRepository.findAllByRole(Role.ROLE_USER);

        List<UserResponseDTO> userResponseList = new ArrayList<>();

        userList.forEach( (user)->{
            UserResponseDTO userResponseDTO = new UserResponseDTO();

            userResponseDTO.setUuid( user.getUuid() );
            userResponseDTO.setName( user.getName());
            userResponseDTO.setEmail( user.getEmail());
            userResponseDTO.setRole( user.getRole());
            userResponseDTO.setPhoneNo( user.getPhoneNo());
            userResponseDTO.setAccountStatus( user.getAccountStatus());
            userResponseDTO.setCreatedAt( user.getCreatedAt());
            userResponseDTO.setInvoiceGenerated(user.isInvoiceGenerated());
            userResponseDTO.setAccountAccess(user.isAccountAccess());

            userResponseList.add(userResponseDTO);
        });
        log.info("Get all users list");
        return userResponseList;
    }

    @Override
    public List<PlanResponseDTO> getSubscribedPlanList(UUID uuid) {
     return userService.getSubscribedPlansList(uuid);
    }

    @Override
    public AdminDashboardResponseDTO getDashboardDetails() {

        AdminDashboardResponseDTO adminDashboardResponseDTO = new AdminDashboardResponseDTO();

        try {
            int totalUsers = userRepository.countByRole(Role.ROLE_USER);
            int totalPlans = planRepository.countAll();

            log.info("plan repo" + totalPlans);

            int totalSubscribedPlans = (int) userSubscriptionDetailsRepo.count();
            int invoiceGenerated = (int) invoiceRepo.count();
            int invoicePaid = invoiceRepo.countAllByStatus("PAID");
            int totalMembers = userRepository.countByRole(Role.ROLE_MEMBER);

            int accountActive = userRepository.countUserStatus("ACTIVE", Role.ROLE_USER);
//            int accountActive = 10;

            int accountTerminated = userRepository.countUserStatus("TERMINATED", Role.ROLE_USER);
            int accountSuspended = userRepository.countUserStatus("SUSPENDED", Role.ROLE_USER);

            adminDashboardResponseDTO.setActiveAccount( accountActive );
            adminDashboardResponseDTO.setInvoicePaid( invoicePaid);
            adminDashboardResponseDTO.setInvoiceGenerated(invoiceGenerated);
            adminDashboardResponseDTO.setTerminatedAccount(accountTerminated);
            adminDashboardResponseDTO.setSuspendedAccount(accountSuspended);
            adminDashboardResponseDTO.setTotalMembers( totalMembers);
            adminDashboardResponseDTO.setTotalPlans( totalPlans);
            adminDashboardResponseDTO.setTotalUsers( totalUsers);
            adminDashboardResponseDTO.setNosOfPlansSubscribed( totalSubscribedPlans);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        log.info("Admin Dashboard Data");
       return adminDashboardResponseDTO;

    }


    public UserResponseDTO toggleAccountAccessService(UUID uid){
        // Default every account access will be true..
        User user = userRepository.findByUuid(uid)
                .orElseThrow(()-> new RuntimeException("User not found/Invalid UUID"));

        user.setAccountAccess( !user.isAccountAccess());

        User savedUser = userRepository.save(user);

        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setUuid( savedUser.getUuid() );
        userResponseDTO.setName( savedUser.getName());
        userResponseDTO.setEmail( savedUser.getEmail());
        userResponseDTO.setRole( savedUser.getRole());
        userResponseDTO.setPhoneNo( savedUser.getPhoneNo());
        userResponseDTO.setAccountStatus( savedUser.getAccountStatus());
        userResponseDTO.setCreatedAt( savedUser.getCreatedAt());
        userResponseDTO.setInvoiceGenerated(savedUser.isInvoiceGenerated());
        userResponseDTO.setAccountAccess(savedUser.isAccountAccess());

        return userResponseDTO;
    }

}
