package com.rz.resilience4jinbankingsystem.controller;

import com.rz.resilience4jinbankingsystem.service.BankingSystemService;
import com.rz.resilience4jinbankingsystem.service.BankingSystemServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class BankingSystemController {

    @Autowired
    BankingSystemServiceImpl bankingSystemService;
    @PostMapping("/deposit")
    public ResponseEntity<String> deposit() {
//        if (bankingSystemService.authenticateUser(account)) {
//            double exchangeRate = bankingSystemService.getExchangeRate("USD", "EUR");
//            double convertedAmount = amount * exchangeRate;
            String paymentResult = bankingSystemService.processPayment("123456", 1500);
//            bankingSystemService.sendNotification("Deposit completed for account: " + account);
            return ResponseEntity.ok(paymentResult);
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed.");
    }
}
