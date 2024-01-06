package com.prodapt.billingsystem.api.subscription.entity.dto;

import com.prodapt.billingsystem.api.membersubscription.dto.MemberAccountSubsRequestDTO;
import com.prodapt.billingsystem.api.user.dto.MemberAccountRequestDTO;
import com.prodapt.billingsystem.api.user.entity.MemberAccountEntity;
import lombok.Data;

import java.util.List;

@Data
public class SubscriptionRequestDTO {
    private String parentUuid;
    private List<MemberAccountSubsRequestDTO> membersList;
}
