package com.internship.nbpapi.model;

import lombok.Data;

import java.util.List;

@Data
public class ExchangeRatesSeries {

    private String table;
    private String currency;
    private String code;
    private List<Rate> rates;
}
