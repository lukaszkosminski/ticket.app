package com.example.ticket.controller;

import com.example.ticket.model.Currency;
import com.example.ticket.model.JobType;
import com.example.ticket.model.Person;
import com.example.ticket.model.Ticket;
import com.example.ticket.model.dto.CreatePersonDTO;
import com.example.ticket.model.dto.CreateTicketDTO;
import com.example.ticket.model.dto.mapper.PersonMapper;
import com.example.ticket.model.dto.mapper.TicketMapper;
import com.example.ticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @Value("${ticket.default-administrative-fee}")
    private BigDecimal defaultAdministrativeFee;

    @GetMapping("/add")
    public String addTicketsForm(Model model) {
        model.addAttribute("ticket", new CreateTicketDTO());
        model.addAttribute("person", new CreatePersonDTO());
        model.addAttribute("jobTypes", JobType.values());
        model.addAttribute("currencies", Currency.values());
        return "add";
    }

    @PostMapping("/ticket/{id}/pay")
    public String markTicketAsPaid(@PathVariable Long id, Model model) {
        ticketService.markAsPaid(id);
        model.addAttribute("message", "Ticket został oznaczony jako opłacony.");
        return "redirect:/";
    }

    @GetMapping("/")
    public String listTickets(Model model) {
        model.addAttribute("tickets", TicketMapper.toListDTO(ticketService.findAll()));
        return "list";
    }

    @PostMapping("/add")
    public String addTicket(@Valid @ModelAttribute("ticket") CreateTicketDTO ticket,
                            @Valid @ModelAttribute("person") CreatePersonDTO person,
                            BindingResult result, Model model) {
        try {
            Person personEntity = PersonMapper.toEntity(person);
            Ticket ticketEntity = TicketMapper.toEntity(ticket);
            ticketEntity.setAdministrativeFee(defaultAdministrativeFee);
            ticketEntity.setPerson(personEntity);
            ticketService.savePerson(personEntity);
            ticketService.saveTicket(ticketEntity);
            model.addAttribute("message", "Gratulacje! Udało się dodać ticket do systemu");
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            result.rejectValue("signature", "error.signature", e.getMessage());
            model.addAttribute("jobTypes", JobType.values());
            model.addAttribute("currencies", Currency.values());
            return "add";
        }
    }
}