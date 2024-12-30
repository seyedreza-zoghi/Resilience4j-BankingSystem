package com.rz.resilience4jinbankingsystem.controller;


import com.rz.resilience4jinbankingsystem.service.CountriesService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import java.util.List;

@RestController
public class CountriesController {


    private final CountriesService countriesService;

    public CountriesController(CountriesService countriesService) {
        this.countriesService = countriesService;
    }

    @GetMapping("/countries")
    @CircuitBreaker(name = "countriesCircuitBreaker", fallbackMethod = "restClientException")
    public List<Object> getCountries() throws Exception {
        return countriesService.getCountries();
    }

    public List<Object> restClientException(Throwable throwable) {
        throw new RestClientException("this not available . . .");
    }

}
