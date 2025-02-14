package TicketingGateway.service;

import TicketingGateway.domain.EmailMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * @author GesangZeren
 * @project TicketingGateway
 * @date 1/1/2025
 */
@Service
public class EmailListenerService {

    @JmsListener(destination = "emailQueue")
    public void receiveMessage(EmailMessage emailMessage) {
        System.out.println("Received email message:");
        System.out.println("To: " + emailMessage.getToEmail());
        System.out.println("Subject: " + emailMessage.getSubject());
        System.out.println("Body: " + emailMessage.getBody());

    }
}
