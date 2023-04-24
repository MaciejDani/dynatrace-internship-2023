package com.internship.nbpapi.controller;

import com.internship.nbpapi.model.MaxAndMinAverage;
import com.internship.nbpapi.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/exchanges")
@RequiredArgsConstructor
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @GetMapping("/{currencyCode}/{date}")
    public ResponseEntity<Double> getExchangeRate(
            @PathVariable String currencyCode,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        Double averageRate = exchangeRateService.getExchangeRate(currencyCode, date);

        return ResponseEntity.ok(averageRate);
    }

    @GetMapping("/{currencyCode}/last/{numberOfQuotations}")
    public ResponseEntity<MaxAndMinAverage> getMaxAndMinAverageValue(
            @PathVariable String currencyCode,
            @PathVariable int numberOfQuotations) {

        MaxAndMinAverage result = exchangeRateService.getMaxAndMinAverageValue(currencyCode, numberOfQuotations);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{currencyCode}/last/{numberOfQuotations}/difference")
    public ResponseEntity<Double> getMajorDifference(
            @PathVariable String currencyCode,
            @PathVariable int numberOfQuotations) {

        Double result = exchangeRateService.getMajorDifference(currencyCode, numberOfQuotations);

        return ResponseEntity.ok(result);
    }

}
