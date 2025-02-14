package TicketingGateway.controller;

import TicketingGateway.service.TicketNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * @author GesangZeren
 * @project TicketingGateway
 * @date 1/1/2025
 */
@RestController
public class EmailController {

    @Autowired
    private TicketNotificationService notificationService;


    @PostMapping("/send-email")
    public String sendEmailToQueue(@RequestParam String to,
                                   @RequestParam String subject,
                                   @RequestParam String body) {
        notificationService.sendEmailToQueue(to, subject, body);
        return "Email message sent to queue.";
    }
}

