package com.example.ticket.service;

import com.example.ticket.model.Person;
import com.example.ticket.model.Ticket;
import com.example.ticket.repo.PersonRepository;
import com.example.ticket.repo.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final PersonRepository personRepository;
    private final JavaMailSender mailSender;

    @Value("${email.sender}")
    private String emailSender;

    @Value("${ticket.default-administrative-fee}")
    private BigDecimal defaultAdministrativeFee;

    public void saveTicket(Ticket ticket) {
        log.info("Attempting to save ticket with signature: {}", ticket.getSignature());
        validateSignatureUniqueness(ticket.getSignature());
        ticket.setPaid(false);
        ticket.setFineAmount(calculateTotalCostTicket(ticket));
        ticketRepository.save(ticket);
        log.info("Ticket with signature {} saved successfully", ticket.getSignature());
        sendNotificationEmail(ticket);
    }

    public void markAsPaid(Long ticketId) {
        log.info("Marking ticket with ID {} as paid", ticketId);
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> {
            log.error("Ticket with ID {} not found", ticketId);
            return new RuntimeException("Ticket not found");
        });
        ticket.setPaid(true);
        ticketRepository.save(ticket);
        log.info("Ticket with ID {} marked as paid", ticketId);
    }

    public List<Ticket> findAll() {
        log.info("Fetching all tickets");
        return ticketRepository.findAll();
    }

    public void savePerson(Person person) {
        log.info("Saving person", person.getId());
        personRepository.save(person);
        log.info("Person with ID {} saved successfully", person.getId());
    }

    public List<Ticket> findByLastName(String lastName) {
        log.info("Searching for tickets by last name: {}", lastName);
        return ticketRepository.findByPersonLastNameContainingIgnoreCase(lastName);
    }

    public List<Ticket> findBySignature(String signature) {
        log.info("Searching for tickets by signature: {}", signature);
        return ticketRepository.findBySignatureContainingIgnoreCase(signature);
    }

    public List<Ticket> findByPhoneNumber(String phoneNumber) {
        log.info("Searching for tickets by phone number: {}", phoneNumber);
        return ticketRepository.findByPersonPhoneNumberContaining(phoneNumber);
    }

    private void sendNotificationEmail(Ticket ticket) {
        log.info("Sending notification email for ticket with signature: {}", ticket.getSignature());
        String to = ticket.getPerson().getEmail();
        String subject = "Ticket nr. " + ticket.getSignature() + " prośba o opłacenie";
        String text = "Dzień dobry, Pragniemy poinformować, że właśnie został dodany do naszej aplikacji ticket o numerze "
                + ticket.getSignature() + " na kwotę " + ticket.getFineAmount() + " " + ticket.getCurrency()
                + ". Prosimy o dokonanie wpłaty do dnia " + ticket.getPaymentDueDate()
                + ". Szczegółowe informacje dotyczące ticketu znajdziesz pod linkiem: [link do ticketu]."
                + " Wiadomość wysłana automatycznie. Nie odpowiadaj na nią.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailSender);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        try {
            mailSender.send(message);
            log.info("Notification email sent to: {} for ticket signature: {}", to, ticket.getSignature());
        } catch (Exception e) {
            log.error("Failed to send email to: {} for ticket signature: {}", to, ticket.getSignature(), e);
        }
    }

    private BigDecimal calculateTotalCostTicket(Ticket ticket) {
        BigDecimal administrativeFee = defaultAdministrativeFee;
        BigDecimal totalCost = ticket.getFineAmount().add(administrativeFee);
        log.info("Calculated total cost for ticket with signature {}: {}", ticket.getSignature(), totalCost);
        return totalCost;
    }

    private void validateSignatureUniqueness(String signature) {
        log.info("Validating uniqueness of signature: {}", signature);
        if (ticketRepository.existsBySignature(signature)) {
            log.error("Signature '{}' already exists", signature);
            throw new IllegalArgumentException("Signature '" + signature + "' already exists. Please use a unique signature.");
        }
        log.info("Signature '{}' is unique", signature);
    }
}