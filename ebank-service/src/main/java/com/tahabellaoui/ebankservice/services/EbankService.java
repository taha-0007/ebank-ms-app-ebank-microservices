package com.tahabellaoui.ebankservice.services;

import com.tahabellaoui.ebankservice.entities.BankAccount;
import com.tahabellaoui.ebankservice.model.Customer;
import com.tahabellaoui.ebankservice.repository.BankAccountRepository;
import com.tahabellaoui.ebankservice.feign.CustomerRestClient;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EbankService {

    private final BankAccountRepository accountRepository;
    private final CustomerRestClient customerRestClient;

    // Injection via le constructeur
    public EbankService(BankAccountRepository accountRepository, CustomerRestClient customerRestClient) {
        this.accountRepository = accountRepository;
        this.customerRestClient = customerRestClient;
    }

    public List<BankAccount> getAllBankAccounts() {
        List<BankAccount> bankAccounts = accountRepository.findAll();

        bankAccounts.forEach(bankAccount -> {
            try {
                bankAccount.setCustomer(
                        customerRestClient.getCustomerById(bankAccount.getCustomerId())
                );
            } catch (Exception e) {
                // Au cas où le customer-service est éteint, on affiche un client par défaut
                bankAccount.setCustomer(new Customer(bankAccount.getCustomerId(), "Not available", "Not available"));
            }
        });

        return bankAccounts;
    }

    public BankAccount getBankAccountById(String id) {
        // ⚠️ C'est ICI que l'on déclare la variable "bankAccount"
        BankAccount bankAccount = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Maintenant on peut l'utiliser pour appeler le client Feign
        bankAccount.setCustomer(
                customerRestClient.getCustomerById(bankAccount.getCustomerId())
        );

        return bankAccount;
    }
    public BankAccount save(BankAccount bankAccount) {
        try {
            Customer customer = customerRestClient.getCustomerById(bankAccount.getCustomerId());

            bankAccount.setId(UUID.randomUUID().toString());
            bankAccount.setCreatedAt(new Date());

            return accountRepository.save(bankAccount);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}