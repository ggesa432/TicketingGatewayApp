package TicketingGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJms
public class TicketingGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketingGatewayApplication.class, args);
    }

}

//iText, genenerate a PDF summary of ticket details, and who approved the ticket, whom picked the ticket, like a conclusion.
//Use jms queue mechanism to send email to mutiple people, can be applied in the cron job https://www.baeldung.com/spring-jms
//when a ticket is closed, it can be reopen within 7 days, when it reopen, anybody can pick it up, and can see the previous history, like who worked on the
//ticket, waht was the issue, and can resolve the issue, the complete history could be seen by the user.
//When creating ticketing, on same screen to do the development, you should be able to upload the screenshots or images of the issue,
//you should have option you can upload mutiple images, admin when viewing the issue, they have more information