package com.example.ticket.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.ticket.model.Currency;
import com.example.ticket.model.Person;
import com.example.ticket.model.Ticket;
import com.example.ticket.repo.PersonRepository;
import com.example.ticket.repo.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private JavaMailSender mailSender;

    @Value("test@sender.com")
    private String emailSender;

    @Value("10")
    private BigDecimal defaultAdministrativeFee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveTicket_ShouldSaveAndSendEmail() {

        Ticket ticket = new Ticket();
        ticket.setSignature("ABC123");
        ticket.setFineAmount(BigDecimal.valueOf(100));
        ticket.setCurrency(Currency.PLN);
        ticket.setPaymentDueDate(LocalDate.now());

        Person person = new Person();
        person.setEmail("test@example.com");
        ticket.setPerson(person);

        when(ticketRepository.existsBySignature("ABC123")).thenReturn(false);


        ReflectionTestUtils.setField(ticketService, "defaultAdministrativeFee", BigDecimal.TEN);


        ticketService.saveTicket(ticket);


        verify(ticketRepository).save(ticket);
        verify(mailSender).send(any(SimpleMailMessage.class));
        assertFalse(ticket.isPaid());
        assertEquals(BigDecimal.valueOf(110), ticket.getFineAmount());
    }

    @Test
    void testMarkAsPaid_ShouldMarkTicketAsPaid() {

        Ticket ticket = new Ticket();
        ticket.setPaid(false);

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));


        ticketService.markAsPaid(1L);


        assertTrue(ticket.isPaid());
        verify(ticketRepository).save(ticket);
    }

    @Test
    void testMarkAsPaid_ShouldThrowExceptionIfTicketNotFound() {

        when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> ticketService.markAsPaid(1L));
        assertEquals("Ticket not found", exception.getMessage());
    }

    @Test
    void testFindAll_ShouldReturnAllTickets() {

        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket());

        when(ticketRepository.findAll()).thenReturn(tickets);

        List<Ticket> result = ticketService.findAll();

        assertEquals(1, result.size());
    }

    @Test
    void testFindByLastName_ShouldReturnMatchingTickets() {

        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket());

        when(ticketRepository.findByPersonLastNameContainingIgnoreCase("Doe")).thenReturn(tickets);

        List<Ticket> result = ticketService.findByLastName("Doe");

        assertEquals(1, result.size());
    }

    @Test
    void testFindBySignature_ShouldReturnMatchingTickets() {

        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket());

        when(ticketRepository.findBySignatureContainingIgnoreCase("ABC123")).thenReturn(tickets);

        List<Ticket> result = ticketService.findBySignature("ABC123");

        assertEquals(1, result.size());
    }

    @Test
    void testFindByPhoneNumber_ShouldReturnMatchingTickets() {

        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket());

        when(ticketRepository.findByPersonPhoneNumberContaining("123456789"))
                .thenReturn(tickets);

        List<Ticket> result = ticketService.findByPhoneNumber("123456789");

        assertEquals(1, result.size());
    }

    @Test
    void testSavePerson_ShouldSuccessfullySavePersonWithValidData() {
        Person person = new Person();
        person.setId(1L);
        person.setFirstName("John");
        person.setLastName("Doe");

        ticketService.savePerson(person);

        verify(personRepository).save(person);
    }

}
