package com.prodapt.billingsystem.api.auth.dto;

import lombok.Data;

@Data
public class SigninRequest {
    private String email;
    private String password;

//    public String getEmail() {
//        return email;
//    }
//    public void setEmail(String email) {
//        this.email = email;
//    }
//    public String getPassword() {
//        return password;
//    }
//    public void setPassword(String password) {
//        this.password = password;
//    }
}
