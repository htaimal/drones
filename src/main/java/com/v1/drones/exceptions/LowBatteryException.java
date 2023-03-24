package com.v1.drones.exceptions;


public class LowBatteryException extends RuntimeException {
    public LowBatteryException(String message){
        super(message);
    }
}
