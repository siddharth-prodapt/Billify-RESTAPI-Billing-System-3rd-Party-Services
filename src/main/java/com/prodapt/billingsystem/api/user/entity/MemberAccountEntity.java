package com.prodapt.billingsystem.api.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Timestamp;
import java.util.UUID;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberAccountEntity {

    @JsonIgnore
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;


    @UuidGenerator
    @Column(unique = true)
    private UUID uuid;

    private String name;
    private String phoneNo;
    private String gender;

    private Long parentUserId; //reference to user table

    private String createdAt;
    private String modifiedAt;

    @PrePersist
    protected void onCreate() {

        createdAt = String.valueOf(new Timestamp(System.currentTimeMillis()));
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedAt = String.valueOf(new Timestamp(System.currentTimeMillis()));
    }

}
