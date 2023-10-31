package com.prodapt.billingsystem.api.admin.dto;

import lombok.Data;

@Data
public class AdminDashboardResponseDTO {

    /*
     * Total users
     * total plans
     * nos of plans subscribed
     * invoiceGenerated
     * invoice paid
     *
     * totalMembers
     *
     * account active
     * account terminated
     * account suspended
     * */
    private int totalUsers;
    private int totalPlans;
    private int nosOfPlansSubscribed;
    private int invoiceGenerated;
    private int invoicePaid;
    private int totalMembers;
    private int activeAccount;
    private int terminatedAccount;
    private int suspendedAccount;
}
