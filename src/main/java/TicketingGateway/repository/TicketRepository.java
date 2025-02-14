package TicketingGateway.repository;

import TicketingGateway.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author GesangZeren
 * @project TicketingGateway
 * @date 11/12/2024
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT t FROM Ticket t WHERE t.status = :status AND t.createdAt < :thresholdTime")
    List<Ticket> findByStatusAndCreatedAtBefore(String status, LocalDateTime thresholdTime);

}
