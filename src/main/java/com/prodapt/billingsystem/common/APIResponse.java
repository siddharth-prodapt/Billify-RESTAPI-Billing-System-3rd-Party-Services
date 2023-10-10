package com.prodapt.billingsystem.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class APIResponse<T>{
    private int statusCode;
    private String message;
    private T data;

    public APIResponse(){
        this.statusCode = 200;
        this.message = "OK";
    }
    public APIResponse(T data) {
        this.statusCode = 200;
        this.message = "OK";
        this.data = data;
    }
}
