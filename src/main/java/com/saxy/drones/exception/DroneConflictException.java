package com.saxy.drones.exception;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class DroneConflictException extends Exception{
    private int code;

    public DroneConflictException(String message){
        super(message);
        code = HttpStatus.BAD_REQUEST.value();
    }

    public DroneConflictException(int code,String message){
        this(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
