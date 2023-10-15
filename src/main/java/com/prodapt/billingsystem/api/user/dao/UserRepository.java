package com.prodapt.billingsystem.api.user.dao;

import com.prodapt.billingsystem.api.user.entity.Role;
import com.prodapt.billingsystem.api.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long>, CrudRepository<User, Long> {
//    @Query("select DISTINCT from customer  where name=:name and password=:password ")
    User findCustomerByNameAndPassword(String name, String password);
    User findCustomerByName(String name);
    Optional<User> findUserByIdAndRole(Long id, Role role);
    User findUserByEmail(String email);
    Optional<User> findByEmail(String email);
    User findByRole(Role role);
    Optional<List<User>> findUsersByParentUserIdAndRole(Long parentUserId, Role role);

    Optional<User> findByUuid(UUID uuid);
}
