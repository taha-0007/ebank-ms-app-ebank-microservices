package com.tahabellaoui.ebankservice.controller;

import com.tahabellaoui.ebankservice.entities.BankAccount;
import com.tahabellaoui.ebankservice.services.EbankService;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
public class EbankRestController {

    private EbankService ebankService;

    // Constructeur pour l'injection de dépendance
    public EbankRestController(EbankService ebankService) {
        this.ebankService = ebankService;
    }

    // 1. Récupérer tous les comptes (GET)
    @GetMapping("/accounts")
    public List<BankAccount> getAllBankAccounts() {
        return ebankService.getAllBankAccounts();
    }

    // 2. Récupérer un compte par son ID (GET)
    @GetMapping("/accounts/{id}")
    public BankAccount getBankAccountById(@PathVariable String id) {
        return ebankService.getBankAccountById(id);
    }

    // 3. Sauvegarder un nouveau compte (POST)
    @PostMapping("/accounts")
    public BankAccount save(@RequestBody BankAccount bankAccount) {
        // J'ai ajouté "return" ici, car la méthode du service renvoie le compte sauvegardé
        return ebankService.save(bankAccount);
    }
}