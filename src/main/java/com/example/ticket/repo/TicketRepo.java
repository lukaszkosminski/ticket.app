package com.example.ticket.repo;

import com.example.ticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepo extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findBySignature(String signature);
    List<Ticket> findByStatusOrderByAmount(String status);

//    List<Ticket> filterAndSortTickets(String status, String sort);

//    List<Ticket> findByStatusOrderByField(String status, String sort);

    List<Ticket> findByStatus(String status);

//    List<Ticket> findAllOrderByField(String sort);

    List<Ticket> findByStatusOrderBySignature(String status);

    List<Ticket> findByStatusOrderByStatus(String status);

    List<Ticket> findAllByOrderBySignature();

    List<Ticket> findAllByOrderByAmount();

    List<Ticket> findAllByOrderByStatus();
}
