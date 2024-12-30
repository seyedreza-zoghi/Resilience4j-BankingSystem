package com.rz.resilience4jinbankingsystem.controller;


import com.rz.resilience4jinbankingsystem.service.CountriesService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class CountriesController {


    private final CountriesService countriesService;

    public CountriesController(CountriesService countriesService) {
        this.countriesService = countriesService;
    }

    @GetMapping("/circuitBreaker")
    @CircuitBreaker(name = "callExternalServiceCircuitBreaker", fallbackMethod = "restClientException")
    public List<Object> callExternalServiceCircuitBreaker() throws Exception {
        if (true) throw new Exception("....... . .");
        return countriesService.getCountries();
    }

    @GetMapping("/retry")
    @Retry(name = "callExternalServiceRetry", fallbackMethod = "fallbackResponse")
    public String callExternalServiceRetry() {
        throw new RuntimeException("Service failed");
    }

    @GetMapping("/rateLimiter")
    @RateLimiter(name = "callExternalServiceRateLimiter", fallbackMethod = "fallbackResponse")
    public String callExternalServiceRateLimiter() {
        throw new RuntimeException("Service failed");
    }

    @GetMapping("/timeLimiter")
    @TimeLimiter(name = "callExternalServiceTimeLimiter", fallbackMethod = "fallbackResponseTimeLimiter")
    public CompletableFuture<String> callExternalService() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Success!";
        });
    }


    public String fallbackResponse(Exception e) {
        return "Fallback response: " + e.getMessage();
    }

    public List<Object> restClientException(Throwable throwable) {
        throw new RestClientException("this not available . . .");
    }
    public CompletableFuture<String> fallbackResponseTimeLimiter(Throwable t) {
        return CompletableFuture.completedFuture("Fallback response: " + t.getMessage());
    }

}
