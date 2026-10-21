package com.tahabellaoui.ebankservice;

import com.tahabellaoui.ebankservice.entities.BankAccount;
import com.tahabellaoui.ebankservice.services.EbankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EbankServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(EbankService ebankService) {
        return args -> {
            for (int i = 1; i <= 3; i++) {
                for (int j = 0; j < 5; j++) {
                    ebankService.save(BankAccount.builder()
                            // Le professeur écrit "CURRENT-ACCOUNT" avec un tiret
                            .type(Math.random() > 0.5 ? "CURRENT-ACCOUNT" : "SAVING-ACCOUNT")
                            // Solde entre 1000 et 61000
                            .balance(1000 + Math.random() * 60000)
                            // On lie le compte au client (i = 1, 2 ou 3)
                            .customerId(i)
                            .build());
                }
            }
        };
    }
}