package br.com.sgpr.teste.business.util;

import org.springframework.stereotype.Component;

@Component
public class EmailSender {
    private String recipient;
    private String content;

    public String getRecipient() {
        return recipient;
    }
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public void send() {
        System.out.println("Seding email to " + recipient);
        System.out.println("content: " + content);
    }
}
