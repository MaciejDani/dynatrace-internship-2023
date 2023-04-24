package com.internship.nbpapi.exception;

public class NoDataAvailableException extends RuntimeException{

    public NoDataAvailableException() {
        super("No data available.");
    }
}
