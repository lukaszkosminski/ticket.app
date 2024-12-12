package com.example.ticket.service;
import com.example.ticket.model.Ticket;
import com.example.ticket.repo.TicketRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepo ticketRepo;

    public Ticket addTicket(Ticket ticket) {
        if (ticketRepo.findBySignature(ticket.getSignature()).isPresent()) {
            throw new IllegalArgumentException("Mandat z wpisaną sygnaturą już istnieje w systemie");
        }
        return ticketRepo.save(ticket);
    }

    public List<Ticket> getAllFines() {
        return ticketRepo.findAll();
    }

    public Object findByStatusOrderByAmount(String status) {
        return ticketRepo.findByStatusOrderByAmount(status);
    }

//    public Object filterAndSortTickets(String status, String sort) {
//        return ticketRepo.filterAndSortTickets(status, sort);
//    }
//public List<Ticket> filterAndSortTickets(String status, String sort) {
//    // Implement the logic to filter by status and sort by the specified field
//    // This is a placeholder implementation and should be replaced with actual logic
//    if (status != null && sort != null) {
//        return ticketRepo.findByStatusOrderByField(status, sort);
//    } else if (status != null) {
//        return ticketRepo.findByStatus(status);
//    } else if (sort != null) {
//        return ticketRepo.findAllOrderByField(sort);
//    } else {
//        return ticketRepo.findAll();
//    }

    public List<Ticket> filterAndSortTickets(String status, String sort) {
        if (status != null) {
            return switch (sort) {
                case "signature" -> ticketRepo.findByStatusOrderBySignature(status);
                case "amount" -> ticketRepo.findByStatusOrderByAmount(status);
                case "status" -> ticketRepo.findByStatusOrderByStatus(status);
                default -> ticketRepo.findByStatus(status);
            };
        } else {
            return switch (sort) {
                case "signature" -> ticketRepo.findAllByOrderBySignature();
                case "amount" -> ticketRepo.findAllByOrderByAmount();
                case "status" -> ticketRepo.findAllByOrderByStatus();
                default -> ticketRepo.findAll();
            };
        }
}
}
