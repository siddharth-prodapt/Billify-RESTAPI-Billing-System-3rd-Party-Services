package com.prodapt.billingsystem.api.user.dao;

import com.prodapt.billingsystem.api.user.entity.Role;
import com.prodapt.billingsystem.api.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, CrudRepository<User, Long> {
//    @Query("select DISTINCT from customer  where name=:name and password=:password ")
    User findCustomerByNameAndPassword(String name, String password);

    User findCustomerByName(String name);

    User findUserById(Long id);
    User findUserByEmail(String email);

    Optional<User> findByEmail(String email);
    User findByRole(Role role);
}
