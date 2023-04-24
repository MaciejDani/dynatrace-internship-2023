package com.internship.nbpapi.exception;

public class InvalidDateRangeException extends RuntimeException{

    public InvalidDateRangeException() {
        super("Invalid date range.");
    }
}
