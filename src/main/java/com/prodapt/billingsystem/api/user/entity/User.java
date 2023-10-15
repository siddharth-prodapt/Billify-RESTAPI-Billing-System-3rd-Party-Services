package com.prodapt.billingsystem.api.user.entity;

import com.prodapt.billingsystem.api.plans.entity.Plan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.*;

@Setter@Getter@NoArgsConstructor@AllArgsConstructor
@Entity
@Table(name="user")
public class User implements UserDetails {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator
    @Column(unique = true)
    private UUID uuid = UUID.randomUUID();

    private String name;
    private String password;
    private String phoneNo;
    private String email;
    private String dateOfBirth;
    private String pincode;
    private String city;
    private String state;
    private String country;
    private boolean isParentUser;
    private String createdAt;
    private String modifiedAt;
    private Role role;

    private Long parentUserId;

    @ColumnDefault("1")
    private boolean isAvailable;


    @PrePersist
    protected void onCreate() {
        createdAt = String.valueOf(new Date());
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedAt = String.valueOf(new Date());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public String getPassword() {
        return this.password;
    }

}
