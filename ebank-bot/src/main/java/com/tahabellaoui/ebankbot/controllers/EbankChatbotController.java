package com.tahabellaoui.ebankbot.controllers;

import com.tahabellaoui.ebankbot.agents.EbankAIAgent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EbankChatbotController {

    private final EbankAIAgent ebankAIAgent;

    // Le contrôleur injecte maintenant l'Agent, et non plus ChatClient
    public EbankChatbotController(EbankAIAgent ebankAIAgent) {
        this.ebankAIAgent = ebankAIAgent;
    }

    @GetMapping("/chat")
    public String chat(
            @RequestParam(name = "query", defaultValue = "Bonjour") String query) {
        // Il délègue tout le travail à l'agent
        return ebankAIAgent.chat(query);
    }
}