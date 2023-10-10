package com.prodapt.billingsystem.api.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Timestamp;

@Setter@Getter@NoArgsConstructor@AllArgsConstructor
@Entity
@Table(name="Customer")
public class User {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
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
    private String role; //user//parentuser //admin

    @ColumnDefault("true")
    private boolean isAvailable;

}
