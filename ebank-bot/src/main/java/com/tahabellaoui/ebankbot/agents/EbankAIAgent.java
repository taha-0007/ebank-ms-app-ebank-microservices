package com.tahabellaoui.ebankbot.agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;

@Service
public class EbankAIAgent {

    private final ChatClient chatClient;

    public EbankAIAgent(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, ToolCallbackProvider tools) {
        this.chatClient = chatClientBuilder
                // 1. Le System Prompt (Le rôle de l'IA)
                .defaultSystem("""
                        Vous êtes un assistant intelligent qui se charge de répondre aux questions 
                        de l'utilisateur à propos des clients et des comptes bancaires, en fonction du contexte.
                        Si aucun contexte n'est fourni ou si vous ne trouvez pas l'information, répondez exactement : JE NE SAIS PAS.
                        """)
                // 2. La mémoire
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                // 3. Les outils MCP (Ta correction est la bonne !)
                .defaultTools(tools)
                .build();
    }

    public String chat(String query) {
        return chatClient.prompt(query)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, "user-1"))
                .call()
                .content();
    }
}