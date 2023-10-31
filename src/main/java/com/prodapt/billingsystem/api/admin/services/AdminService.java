package com.prodapt.billingsystem.api.admin.services;

import com.prodapt.billingsystem.api.admin.dto.AdminDashboardResponseDTO;
import com.prodapt.billingsystem.api.plans.dto.PlanResponseDTO;
import com.prodapt.billingsystem.api.user.dto.UserResponseDTO;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    public List<UserResponseDTO> getAllUsersList();

    List<PlanResponseDTO> getSubscribedPlanList(UUID uuid);

    AdminDashboardResponseDTO getDashboardDetails();

    UserResponseDTO toggleAccountAccessService(UUID uid);
}
