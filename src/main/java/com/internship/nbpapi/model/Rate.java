package com.internship.nbpapi.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Rate {

    private LocalDate effectiveDate;
    private double mid;
    private double bid;
    private double ask;
}
