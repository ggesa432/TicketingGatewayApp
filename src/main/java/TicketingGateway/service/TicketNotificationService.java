package TicketingGateway.service;


import TicketingGateway.domain.EmailMessage;
import TicketingGateway.domain.Ticket;
import TicketingGateway.domain.TicketHistory;
import TicketingGateway.domain.User;
import TicketingGateway.repository.TicketHistoryRepository;
import TicketingGateway.repository.TicketRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author GesangZeren
 * @project TicketingGateway
 * @date 11/18/2024
 */

/*cron job for more than 48 hours ticket status still pending, need to send a email to particular  id (fixed email id )of the list of ticket
* @Scheduled*/

@Service
public class TicketNotificationService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TicketHistoryRepository ticketHistoryRepository;
    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendTicketNotification(String toEmail, String subject, String body) {
        try {
            String emailServiceUrl = "http://localhost:8082/email/send-ticket-notification";

            String encodedToEmail = URLEncoder.encode(toEmail, StandardCharsets.UTF_8.toString());
            String encodedSubject = URLEncoder.encode(subject, StandardCharsets.UTF_8.toString());
            String encodedBody = URLEncoder.encode(body, StandardCharsets.UTF_8.toString());


            // Construct the full URL with query parameters
            URI uri = new URI(emailServiceUrl + "?toEmail=" + encodedToEmail
                    + "&subject=" + encodedSubject
                    + "&body=" + encodedBody);

            restTemplate.postForObject(uri, null, String.class);
            System.out.println("Ticket notification request sent to Email Service");
        } catch (Exception e) {
            System.err.println("Failed to send ticket notification: " + e.getMessage());
        }
    }
    public void sendPasswordResetEmail(String toEmail, String subject, String body,String resetToken) {
        try {
            String emailServiceUrl = "http://localhost:8082/email/send-password-reset";
            String encodedToEmail = URLEncoder.encode(toEmail, StandardCharsets.UTF_8.toString());
            String encodedSubject = URLEncoder.encode(subject, StandardCharsets.UTF_8.toString());
            String encodedBody = URLEncoder.encode(body, StandardCharsets.UTF_8.toString());
            String encodedResetToken = URLEncoder.encode(resetToken, StandardCharsets.UTF_8.toString());

            // Construct the full URL with query parameters
            URI uri = new URI(emailServiceUrl + "?toEmail=" + encodedToEmail
                    + "&subject=" + encodedSubject
                    + "&body=" + encodedBody
                    +"&resetToken=" + encodedResetToken);

            // Make the POST request
            restTemplate.postForObject(uri, null, String.class);
            System.out.println("Password reset email request sent to Email Service");
        } catch (Exception e) {
            System.err.println("Failed to send password reset email: " + e.getMessage());
        }
    }

    public void sendEmailNotificationWithAttachment(String toEmail, String subject, String body, String pdfPath) {
        try {
            String emailServiceUrl = "http://localhost:8082/email/send-with-attachment";

            MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("toEmail", toEmail);
            requestBody.add("subject", subject);
            requestBody.add("body", body);

            // Attach the PDF file
            FileSystemResource file = new FileSystemResource(pdfPath);
            requestBody.add("attachment", file);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            restTemplate.postForObject(emailServiceUrl, requestEntity, String.class);

            System.out.println("Email with attachment sent successfully to Email Service");
        } catch (Exception e) {
            System.err.println("Failed to send email with attachment: " + e.getMessage());
        }
    }
    public void reopenTicket(Long ticketId, User user) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));

        if (!"Closed".equals(ticket.getProgress())) {
            throw new IllegalStateException("Only closed tickets can be reopened.");
        }

        // Check if the ticket is within the 7-day reopen window
        if (ticket.getClosedAt() != null && ticket.getClosedAt().isBefore(LocalDateTime.now().minusDays(7))) {
            throw new IllegalStateException("This ticket can no longer be reopened.");
        }

        // Update ticket status
        ticket.setProgress("In Progress");
        ticket.setStatus("Reopened");
        ticket.setAssignedTo(null); // Reset assignment
        ticket.setReopenedBy(user.getUsername());
        ticket.setReopenedAt(LocalDateTime.now());

        ticketRepository.save(ticket);

        // Log history
        TicketHistory history = new TicketHistory();
        history.setTicket(ticket);
        history.setChangedBy(user.getUsername());
        history.setChangedAt(LocalDateTime.now());
        history.setFieldChanged("status");
        history.setOldValue("Closed");
        history.setNewValue("Reopened");

        ticketHistoryRepository.save(history);
    }

    public void sendEmailToQueue(String email, String subject, String body) {
        EmailMessage emailMessage = new EmailMessage(email, subject, body);
        jmsTemplate.convertAndSend("emailQueue", emailMessage);
        System.out.println("Email message sent to queue: " + emailMessage);
    }

}


