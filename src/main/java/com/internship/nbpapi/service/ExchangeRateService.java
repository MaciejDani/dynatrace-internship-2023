package com.internship.nbpapi.service;

import com.internship.nbpapi.exception.InvalidDateRangeException;
import com.internship.nbpapi.exception.InvalidQuotationsValueException;
import com.internship.nbpapi.exception.NoDataAvailableException;
import com.internship.nbpapi.model.ExchangeRatesSeries;
import com.internship.nbpapi.model.MaxAndMinAverage;
import com.internship.nbpapi.model.Rate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

@Service
public class ExchangeRateService {

    private static final int MAX_QUOTATIONS = 255;
    private static final String EXCHANGE_RATE_PATH = "/a/{currencyCode}/{date}";
    private static final String EXCHANGE_RATE_LAST_PATH = "/{tableName}/{currencyCode}/last/{numberOfQuotations}";
    private final RestTemplate nbpRestTemplate;

    public ExchangeRateService(@Qualifier("nbpRestTemplate") RestTemplate nbpRestTemplate) {
        this.nbpRestTemplate = nbpRestTemplate;
    }

    public Double getExchangeRate(String currencyCode, LocalDate date) {
        checkDate(date);

        ExchangeRatesSeries response = nbpRestTemplate.getForObject(EXCHANGE_RATE_PATH, ExchangeRatesSeries.class, currencyCode, date);

        if (isResponseNotPresent(response)) {
            return null;
        }

        Rate rate = response.getRates().get(0);
        return rate.getMid();
    }

    public MaxAndMinAverage getMaxAndMinAverageValue(String currencyCode, int numberOfQuotations) {
        checkNumberOfQuotations(numberOfQuotations);

        ExchangeRatesSeries response = nbpRestTemplate.getForObject(EXCHANGE_RATE_LAST_PATH, ExchangeRatesSeries.class,"a", currencyCode, numberOfQuotations);

        if (isResponseNotPresent(response)) {
           return null;
        }

        List<Rate> rates = response.getRates();
        DoubleSummaryStatistics stats = rates.stream()
                .mapToDouble(Rate::getMid)
                .summaryStatistics();

        return new MaxAndMinAverage(stats.getMax(), stats.getMin());
    }

    public Double getMajorDifference(String currencyCode, int numberOfQuotations) {
        checkNumberOfQuotations(numberOfQuotations);

        ExchangeRatesSeries response = nbpRestTemplate.getForObject(EXCHANGE_RATE_LAST_PATH, ExchangeRatesSeries.class,"c", currencyCode, numberOfQuotations);

        if (isResponseNotPresent(response)) {
            return null;
        }

        double maxDifference = 0.0;
        for (Rate rate : response.getRates()) {
            double difference = rate.getAsk() - rate.getBid();
            if (difference > maxDifference) {
                maxDifference = difference;
            }
        }
        return maxDifference;
    }

    private void checkNumberOfQuotations(int numberOfQuotations) {
        if (numberOfQuotations < 1 || numberOfQuotations > MAX_QUOTATIONS) {
            throw new InvalidQuotationsValueException();
        }
    }

    private void checkDate(LocalDate date) {
        LocalDate minDate = LocalDate.of(2002, 1, 2);
        if (date.isBefore(minDate)) {
            throw new NoDataAvailableException();
        }
        if (date.isAfter(LocalDate.now())) {
            throw new InvalidDateRangeException();
        }
    }

    private boolean isResponseNotPresent(ExchangeRatesSeries response) {
        return response == null || response.getRates().isEmpty();
    }
}
