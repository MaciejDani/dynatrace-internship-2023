package com.internship.nbpapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyControllerAdvice {

    @ExceptionHandler(InvalidQuotationsValueException.class)
    public ResponseEntity<String> handleInvalidQuotations(InvalidQuotationsValueException invalidQuotationsValueException) {

        return new ResponseEntity<>(invalidQuotationsValueException.getMessage(), HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler
    public ResponseEntity<String> handleInvalidDateRange(InvalidDateRangeException invalidDateRangeException) {

        return new ResponseEntity<>(invalidDateRangeException.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<String> handleNoDataAvailable(NoDataAvailableException noDataAvailableException) {

        return new ResponseEntity<>(noDataAvailableException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
