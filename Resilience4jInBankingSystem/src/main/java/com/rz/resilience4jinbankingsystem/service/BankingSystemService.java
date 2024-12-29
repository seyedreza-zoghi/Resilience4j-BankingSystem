package com.rz.resilience4jinbankingsystem.service;

import java.util.concurrent.CompletableFuture;

public interface BankingSystemService {
    String processPayment(String account, double amount);
    String sendNotification(String message);
    double getExchangeRate(String fromCurrency, String toCurrency);
    boolean authenticateUser(String userId);
    CompletableFuture<String> processPaymentWithTimeout(String account, double amount);
}
