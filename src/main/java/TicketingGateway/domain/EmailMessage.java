package TicketingGateway.domain;

import java.io.Serializable;

/**
 * @author GesangZeren
 * @project TicketingGateway
 * @date 12/11/2024
 */
public class EmailMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    private String toEmail;
    private String subject;
    private String body;

    public EmailMessage() {
    }

    public EmailMessage(String toEmail, String subject, String body) {
        this.toEmail = toEmail;
        this.subject = subject;
        this.body = body;
    }

    // Getters and Setters
    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    @Override
    public String toString() {
        return "EmailMessage{" +
                "toEmail='" + toEmail + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
