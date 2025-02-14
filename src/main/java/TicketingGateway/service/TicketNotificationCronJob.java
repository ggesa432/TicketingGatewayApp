package TicketingGateway.service;

import TicketingGateway.domain.Ticket;
import TicketingGateway.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.List;


/**
 * @author GesangZeren
 * @project TicketingGateway
 * @date 11/20/2024
 */
@Service
public class TicketNotificationCronJob {
    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    RestTemplate restTemplate;

    private static final String EMAIL_SERVICE_URL = "http://localhost:8082/email/send-ticket-notification";
    private static final String NOTIFICATION_EMAIL = "gesangsession66@gmail.com";

//    @Scheduled(cron = "*/30 * * * * *")//30s
//    @Scheduled(cron = "0 0 * * * *")
    @Scheduled(cron = "0 30 8 * * *")//8:30am every day
//    <second> <minute> <hour> <day-of-month> <month> <day-of-week>

    public void sendPendingTicketsNotification() {
        LocalDateTime thresholdTime = LocalDateTime.now().minusHours(48);
        List<Ticket> pendingTickets = ticketRepository.findByStatusAndCreatedAtBefore("Pending", thresholdTime);

        if (!pendingTickets.isEmpty()) {
            StringBuilder emailContent = new StringBuilder("The following tickets have been pending for more than 48 hours:\n\n");

            for (Ticket ticket : pendingTickets) {
                emailContent.append(String.format(
                        "Ticket ID: %d\nDepartment: %s\nCreated At: %s\n\n",
                        ticket.getId(),
                        ticket.getDepartment(),
                        ticket.getCreatedAt()
                ));
            }
            String subject = "Pending Tickets Notification";
            String body = emailContent.toString();
            sendEmailViaMicroservice(NOTIFICATION_EMAIL, subject, body);
            System.out.println("Consolidated email for all pending tickets sent successfully.");

        }
    }


    private void sendEmailViaMicroservice(String toEmail, String subject, String body) {
        try {
            String requestUrl = String.format("%s?toEmail=%s&subject=%s&body=%s",
                    EMAIL_SERVICE_URL,
                    toEmail,
                    subject,
                    body);

            restTemplate.postForObject(requestUrl, null, String.class);
            System.out.println("Notification email request sent to Email Service");
        } catch (Exception e) {
            System.err.println("Failed to send email notification: " + e.getMessage());
        }
    }
}

/*Testing :
INSERT INTO ticket (department, status, created_at)
VALUES ('Finance', 'Pending', NOW() - INTERVAL 49 HOUR);
*/

