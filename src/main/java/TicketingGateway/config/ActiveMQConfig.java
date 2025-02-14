package TicketingGateway.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author GesangZeren
 * @project TicketingGateway
 * @date 1/1/2025
 */
@Configuration
public class ActiveMQConfig {

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL("tcp://localhost:61616");
        factory.setTrustedPackages(List.of("java.util", "java.lang", "java.math", "TicketingGateway.domain"));
        return factory;
    }
}
