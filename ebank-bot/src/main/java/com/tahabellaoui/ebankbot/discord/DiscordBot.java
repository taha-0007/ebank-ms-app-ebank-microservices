package com.tahabellaoui.ebankbot.discord;

import com.tahabellaoui.ebankbot.agents.EbankAIAgent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter; // <-- C'EST CET IMPORT QUI EST CRUCIAL
import org.springframework.stereotype.Component;

@Component
public class DiscordBot extends ListenerAdapter { // <-- IL DOIT ETENDRE ListenerAdapter

    private final EbankAIAgent ebankAIAgent;

    public DiscordBot(EbankAIAgent ebankAIAgent) {
        this.ebankAIAgent = ebankAIAgent;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String query = event.getMessage().getContentRaw();
        if (query == null || query.trim().isEmpty()) return;

        System.out.println("Message reçu de Discord : " + query);

        String response = ebankAIAgent.chat(query);
        event.getChannel().sendMessage(response).queue();
    }
}