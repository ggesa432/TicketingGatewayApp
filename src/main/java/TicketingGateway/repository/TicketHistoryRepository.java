package TicketingGateway.repository;

import TicketingGateway.domain.TicketHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author GesangZeren
 * @project TicketingGateway
 * @date 1/1/2025
 */
@Repository
public interface TicketHistoryRepository extends JpaRepository<TicketHistory, Integer> {
    List<TicketHistory> findByTicketId(Long ticketId);
}
