package com.v1.drones.exceptions;


public class LoadExceededException extends RuntimeException {
    public LoadExceededException(String message){
        super(message);
    }
}
