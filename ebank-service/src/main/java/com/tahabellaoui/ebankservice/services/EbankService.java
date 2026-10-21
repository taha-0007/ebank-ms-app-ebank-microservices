package com.tahabellaoui.ebankservice.services;

import com.tahabellaoui.ebankservice.entities.BankAccount;
import com.tahabellaoui.ebankservice.repository.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EbankService {

    // Injection du Repository
    private BankAccountRepository accountRepository;

    // Constructeur pour l'injection de dépendance
    public EbankService(BankAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Méthode pour récupérer tous les comptes
    public List<BankAccount> getAllBankAccounts() {
        return accountRepository.findAll();
    }

    // Méthode pour récupérer un compte par son ID
    public BankAccount getBankAccountById(String id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    // Méthode pour sauvegarder un compte (Mise à jour selon la capture)
    public BankAccount save(BankAccount bankAccount) {
        // ⚠️ AJOUTS ICI :
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setCreatedAt(new Date());

        return accountRepository.save(bankAccount);
    }
}