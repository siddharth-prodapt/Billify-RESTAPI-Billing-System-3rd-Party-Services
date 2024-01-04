package com.prodapt.billingsystem.api.user.dao;


import com.prodapt.billingsystem.api.user.entity.MemberAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberAccountRepository extends JpaRepository<MemberAccountEntity, Long> {



    @Query(value = "select * from member_account_entity  where parent_user_id = ?1",nativeQuery = true)
    Optional<List<MemberAccountEntity>> findAllByParentUserId(Long id);
}
