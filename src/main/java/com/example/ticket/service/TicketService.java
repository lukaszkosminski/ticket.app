package com.example.ticket.service;

import com.example.ticket.model.Person;
import com.example.ticket.model.Ticket;
import com.example.ticket.repo.PersonRepository;
import com.example.ticket.repo.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.math.BigDecimal;


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
        validateSignatureUniqueness(ticket.getSignature());
        ticket.setPaid(false);
        ticket.setFineAmount(calculateTotalCostTicket(ticket));
        ticketRepository.save(ticket);
        sendNotificationEmail(ticket);
    }

    public boolean isSignatureUnique(String signature) {
        return !ticketRepository.existsBySignature(signature);
    }

    public void markAsPaid(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        ticket.setPaid(true);
        ticketRepository.save(ticket);
    }

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public void savePerson(Person person) {
        personRepository.save(person);
    }

    public List<Ticket> findByLastName(String lastName) {
        return ticketRepository.findByPersonLastNameContainingIgnoreCase(lastName);
    }

    public List<Ticket> findBySignature(String signature) {
        return ticketRepository.findBySignatureContainingIgnoreCase(signature);
    }

    public List<Ticket> findByPhoneNumber(String phoneNumber) {
        return ticketRepository.findByPersonPhoneNumberContaining(phoneNumber);
    }

    private void sendNotificationEmail(Ticket ticket) {

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
        mailSender.send(message);
    }

    private BigDecimal calculateTotalCostTicket(Ticket ticket) {
        BigDecimal administrativeFee = defaultAdministrativeFee;
        return ticket.getFineAmount().add(administrativeFee);
    }

    private void validateSignatureUniqueness(String signature) {
        if (ticketRepository.existsBySignature(signature)) {
            throw new IllegalArgumentException("Signature '" + signature + "' already exists. Please use a unique signature.");
        }
    }
}
