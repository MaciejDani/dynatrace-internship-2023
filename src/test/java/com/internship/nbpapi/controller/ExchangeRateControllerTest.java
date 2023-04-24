package com.internship.nbpapi.controller;

import com.internship.nbpapi.NbpApiApplication;
import com.internship.nbpapi.model.MaxAndMinAverage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = NbpApiApplication.class)
class ExchangeRateControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void getExchangeRateWhenCurrencyGBPAndValidDateThenOK(){
        String currencyCode = "GBP";
        String dateIsoFormat = "2022-03-22";

        ResponseEntity<Double> responseEntity = testRestTemplate.getForEntity("/exchanges/{currencyCode}/{date}",
                Double.class, currencyCode, dateIsoFormat);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(5.6322, responseEntity.getBody());
    }

    @Test
    void getExchangeRateWhenInvalidDateThenBadRequest(){
        String currencyCode = "GBP";
        String dateIsoFormat = "2000-03-22";

        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity("/exchanges/{currencyCode}/{date}",
                String.class, currencyCode, dateIsoFormat);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("No data available.", responseEntity.getBody());
    }

    @Test
    void getMaxAndMinAverageValueCurrencyGBPThenBadRequest(){
        String currencyCode = "GBP";
        int numberOfQuotations = 2;

       ResponseEntity<MaxAndMinAverage> responseEntity = testRestTemplate.getForEntity("/exchanges/{currencyCode}/last/{numberOfQuotations}",
               MaxAndMinAverage.class, currencyCode, numberOfQuotations);

       assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
       assertNotNull(responseEntity.getBody());
       MaxAndMinAverage maxAndMinAverage = responseEntity.getBody();
       assertEquals(5.2176, maxAndMinAverage.getMax());
       assertEquals(5.2086, maxAndMinAverage.getMin());
    }

    @Test
    void getMaxAndMinAverageValueWhenInvalidQuotationsThenBadRequest(){
        String currencyCode = "GBP";
        int numberOfQuotations = 0;

        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity("/exchanges/{currencyCode}/last/{numberOfQuotations}",
                String.class, currencyCode, numberOfQuotations);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull("Invalid number of quotations.", responseEntity.getBody());
    }

    @Test
    void getMajorDifferenceWhenCurrencyGBPThenOK(){
        String currencyCode = "GBP";
        int numberOfQuotations = 2;

        ResponseEntity<Double> responseEntity = testRestTemplate.getForEntity("/exchanges/{currencyCode}/last/{numberOfQuotations}/difference",
                Double.class, currencyCode, numberOfQuotations);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(0.10440000000000005, responseEntity.getBody());
    }

    @Test
    void getMajorDifferenceWhenInvalidQuotationsThenBadRequest(){
        String currencyCode = "GBP";
        int numberOfQuotations = 0;

        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity("/exchanges/{currencyCode}/last/{numberOfQuotations}/difference",
                String.class, currencyCode, numberOfQuotations);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull("Invalid number of quotations.", responseEntity.getBody());
    }
}