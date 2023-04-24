package com.internship.nbpapi.exception;

public class InvalidQuotationsValueException extends RuntimeException{

    public InvalidQuotationsValueException() {
        super("Invalid number of quotations.");
    }
}
