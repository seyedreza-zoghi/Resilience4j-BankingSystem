package com.rz.resilience4jinbankingsystem.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class BankingSystemServiceImpl implements BankingSystemService {
    @Override
    @CircuitBreaker(name = "paymentService", fallbackMethod = "paymentFallback")
    public String processPayment(String account, double amount) {
        if (amount > 1000) {
            throw new RuntimeException("Amount exceeds limit");
        }
        return "Payment successful";
    }

    public String paymentFallback(String account, double amount, Throwable throwable) {
        return "Fallback response due to: " + throwable.getMessage();
    }

    @Override
    @RateLimiter(name = "notificationService", fallbackMethod = "notificationFallback")
    public String sendNotification(String message) {
        return null;
    }

    @Override
    @Retry(name = "currencyService", fallbackMethod = "currencyFallback")
    public double getExchangeRate(String fromCurrency, String toCurrency) {
        return 0;
    }

    @Override
    @Bulkhead(name = "authService", fallbackMethod = "authFallback")
    public boolean authenticateUser(String userId) {
        return false;
    }

    @Override
    @TimeLimiter(name = "paymentServiceWithTimeLimiter", fallbackMethod = "paymentFallbackFuture")
    @CircuitBreaker(name = "paymentServiceWithTimeLimiter")
    public CompletableFuture<String> processPaymentWithTimeout(String account, double amount) {
        return null;
    }

    public String notificationFallback(String message, Throwable t) {
        return "Rate limit exceeded. Please try again later.";
    }

    public double currencyFallback(String fromCurrency, String toCurrency, Throwable t) {
        return 1.0; // Default exchange rate
    }

    public boolean authFallback(String userId, Throwable t) {
        return false;
    }

    public CompletableFuture<String> paymentFallbackFuture(String account, double amount, Throwable t) {
        return CompletableFuture.completedFuture("Payment service timed out. Please try again later.");
    }
}
