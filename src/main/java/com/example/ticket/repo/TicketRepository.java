package com.example.ticket.repo;

import com.example.ticket.model.Currency;
import com.example.ticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findBySignature(String signature);

    boolean existsBySignature(String signature);

    List<Ticket> findByPersonLastNameContainingIgnoreCase(String lastName);

    List<Ticket> findBySignatureContainingIgnoreCase(String signature);

    List<Ticket> findByPersonPhoneNumberContaining(String phoneNumber);
}
