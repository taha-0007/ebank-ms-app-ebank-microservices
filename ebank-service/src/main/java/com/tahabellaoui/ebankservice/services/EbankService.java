package com.tahabellaoui.ebankservice.services;

import com.tahabellaoui.ebankservice.entities.BankAccount;
import com.tahabellaoui.ebankservice.model.Customer;
import com.tahabellaoui.ebankservice.repository.BankAccountRepository;
import com.tahabellaoui.ebankservice.feign.CustomerRestClient;
import org.springframework.ai.mcp.annotation.McpTool;       // <-- IMPORT AJOUTÉ
import org.springframework.ai.mcp.annotation.McpToolParam;    // <-- IMPORT AJOUTÉ
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EbankService {

    private final BankAccountRepository accountRepository;
    private final CustomerRestClient customerRestClient;

    public EbankService(BankAccountRepository accountRepository, CustomerRestClient customerRestClient) {
        this.accountRepository = accountRepository;
        this.customerRestClient = customerRestClient;
    }

    @McpTool(description = "Get all Bank accounts")
    public List<BankAccount> getAllBankAccounts() {
        List<BankAccount> bankAccounts = accountRepository.findAll();

        bankAccounts.forEach(bankAccount -> {
            try {
                bankAccount.setCustomer(
                        customerRestClient.getCustomerById(bankAccount.getCustomerId())
                );
            } catch (Exception e) {
                // Fallback si le customer-service est éteint
                bankAccount.setCustomer(new Customer(bankAccount.getCustomerId(), "Not available", "Not available"));
            }
        });

        return bankAccounts;
    }

    @McpTool(description = "Get a bank account by id")
    public BankAccount getBankAccountById(String id) {
        BankAccount bankAccount = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        bankAccount.setCustomer(
                customerRestClient.getCustomerById(bankAccount.getCustomerId())
        );

        return bankAccount;
    }

    @McpTool(description = "Save a new bank Account")
    public BankAccount save(
            @McpToolParam(description = "The bank account to save (balance, type, customerId)") BankAccount bankAccount) {
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