package com.example.ticket.model.dto.mapper;

import com.example.ticket.model.Ticket;
import com.example.ticket.model.dto.CreateTicketDTO;
import com.example.ticket.model.dto.GetTicketDTO;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
        ticket.setAdministrativeFee(createTicketDTO.getAdministrativeFee());
        return ticket;
    }

    public static List<GetTicketDTO> toListDTO(List<Ticket> tickets) {
        return tickets.stream()
                .map(ticket -> {
                    GetTicketDTO dto = new GetTicketDTO();
                    dto.setId(ticket.getId());
                    dto.setSignature(ticket.getSignature());
                    dto.setOffenseDate(ticket.getOffenseDate());
                    dto.setOffenseReason(ticket.getOffenseReason());
                    dto.setFineAmount(ticket.getFineAmount());
                    dto.setCurrency(ticket.getCurrency());
                    dto.setAdministrativeFee(ticket.getAdministrativeFee());
                    dto.setPaymentDueDate(ticket.getPaymentDueDate());
                    dto.setPaid(ticket.isPaid());
                    dto.setPerson(ticket.getPerson());
                    return dto;
                })
                .collect(Collectors.toList());
    }

}
