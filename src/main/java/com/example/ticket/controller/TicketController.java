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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }


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
        model.addAttribute("tickets", ticketService.findAll());
        return "list";
    }

    @PostMapping("/add")
    public String addTicket(@Valid @ModelAttribute("ticket") CreateTicketDTO ticket,
                            @Valid @ModelAttribute("person") CreatePersonDTO person,
                            BindingResult result, Model model) {
        try {
            Person personEntity = PersonMapper.toEntity(person);
            Ticket ticketEntity = TicketMapper.toEntity(ticket);
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