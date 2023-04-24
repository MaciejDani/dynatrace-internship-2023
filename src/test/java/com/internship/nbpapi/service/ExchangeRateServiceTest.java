package com.internship.nbpapi.service;

import com.internship.nbpapi.exception.InvalidDateRangeException;
import com.internship.nbpapi.exception.InvalidQuotationsValueException;
import com.internship.nbpapi.exception.NoDataAvailableException;

import org.junit.jupiter.api.Test;

import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeRateServiceTest {

    private final ExchangeRateService exchangeRateService = new ExchangeRateService(new RestTemplate());

    @Test
    void getExchangeRateWhenDateBefore2000ThenNoDataAvailableException() {
        LocalDate localDate = LocalDate.of(2000, 2,2);
        String currencyCode = "GBP";

        assertThrows(NoDataAvailableException.class, () -> exchangeRateService.getExchangeRate(currencyCode, localDate), "No data available.");
    }

    @Test
    void getExchangeRateWhenDateAfterTodayThenInvalidDateRangeException() {
        LocalDate localDate = LocalDate.of(2099, 2,2);
        String currencyCode = "GBP";

        assertThrows(InvalidDateRangeException.class, () -> exchangeRateService.getExchangeRate(currencyCode, localDate), "Invalid date range.");
    }

    @Test
    void getMajorDifferenceWhenNumberOfQuotationsLessThan1ThenInvalidQuotationsValueException(){
        int numberOfQuotations = 0;
        String currencyCode = "GBP";

        assertThrows(InvalidQuotationsValueException.class, () -> exchangeRateService.getMajorDifference(currencyCode, numberOfQuotations), "Invalid number of quotations.");
    }

    @Test
    void getMajorDifferenceWhenNumberOfQuotationsMoreThan255ThenInvalidQuotationsValueException(){
        int numberOfQuotations = 256;
        String currencyCode = "GBP";

        assertThrows(InvalidQuotationsValueException.class, () -> exchangeRateService.getMajorDifference(currencyCode, numberOfQuotations), "Invalid number of quotations.");
    }

}