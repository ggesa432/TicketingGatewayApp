package TicketingGateway.repository;

import TicketingGateway.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author GesangZeren
 * @project TicketingGateway
 * @date 11/13/2024
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByResetToken(String resetToken);
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_roles (user_id, role) VALUES (:userId, :role)", nativeQuery = true)
    void insertUserRole(@Param("userId") Long userId, @Param("role") String role);
}
