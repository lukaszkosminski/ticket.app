package com.example.ticket.controller;

import com.example.ticket.model.Ticket;
import com.example.ticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    @GetMapping
    public String listTickets(Model model) {
        model.addAttribute("fines", ticketService.getAllFines());
        return "tickets/list";
    }

    @GetMapping("/add")
    public String addTicketsForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "tickets/add";
    }

//    @PostMapping("/add")
//    public String addTickets(@ModelAttribute Ticket ticket) {
//        ticketService.addTicket(ticket);
//        return "redirect:/fines";
//    }

    @PostMapping("/add")
    public String addFine(@ModelAttribute Ticket ticket, @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            String uploadDir = "uploads/";
            Path path = Paths.get(uploadDir + file.getOriginalFilename());
            try {
                Files.createDirectories(path.getParent());
                Files.write(path, file.getBytes());
                ticket.setAttachmentPath(path.toString());
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Nie udało się zapisać pliku.");
            }
        }
        ticketService.addTicket(ticket);
        return "redirect:/tickets";
    }

    //    @GetMapping("/filter")
//    public String filterFines(@RequestParam String status, Model model) {
//        model.addAttribute("fines", ticketService.findByStatusOrderByAmount(status));
//        return "tickets/list";
//    }
    @GetMapping("/filter")
    public String filterAndSortTickets(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String sort,
            Model model) {
        model.addAttribute("fines", ticketService.filterAndSortTickets(status, sort));
        return "tickets/list";
    }
}
