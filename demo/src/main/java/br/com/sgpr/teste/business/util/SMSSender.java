package br.com.sgpr.teste.business.util;

import org.springframework.stereotype.Component;

@Component
public class SMSSender {
	private String phoneNumber;
    private String message;

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public void send() {
        System.out.println("Seding SMS to " + phoneNumber);
        System.out.println("Message: " + message);
    }
}
