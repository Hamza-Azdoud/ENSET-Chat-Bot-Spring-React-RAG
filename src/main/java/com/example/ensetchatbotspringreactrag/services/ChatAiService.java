package com.example.ensetchatbotspringreactrag.services;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.List;

@BrowserCallable
@AnonymousAllowed
public class ChatAiService {

    private ChatClient chatClient;
    private VectorStore vectorStore;

    public ChatAiService(ChatClient.Builder builder, VectorStore vectorStore) {
        this.chatClient = builder.build();
        this.vectorStore = vectorStore;
    }

    public String ragChat(String question){
        List<Document> documents = vectorStore.similaritySearch(question);
        List<String> context = documents.stream().map(Document::getContent).toList();
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }
}
