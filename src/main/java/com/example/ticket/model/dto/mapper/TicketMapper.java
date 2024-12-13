package com.example.ticket.model.dto.mapper;

import com.example.ticket.model.Ticket;
import com.example.ticket.model.dto.CreateTicketDTO;

public class TicketMapper {

    public static Ticket toEntity(CreateTicketDTO createTicketDTO) {
        Ticket ticket = new Ticket();
        ticket.setSignature(createTicketDTO.getSignature());
        ticket.setOffenseDate(createTicketDTO.getOffenseDate());
        ticket.setOffenseReason(createTicketDTO.getOffenseReason());
        ticket.setFineAmount(createTicketDTO.getFineAmount());
        ticket.setCurrency(createTicketDTO.getCurrency());
        ticket.setPaymentDueDate(createTicketDTO.getPaymentDueDate());
        ticket.setPaid(createTicketDTO.isPaid());
        return ticket;
    }

}
