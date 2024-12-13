package com.example.ticket.model.dto;

import com.example.ticket.model.Currency;
import com.example.ticket.model.Person;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
public class CreateTicketDTO {

    @NotBlank(message = "Sygnatura jest wymagana.")
    @Column(nullable = false, unique = true)
    private String signature;

    @NotNull(message = "Data wykroczenia jest wymagana.")
    @Column(name = "offense_date", nullable = false)
    private LocalDate offenseDate;

    @NotBlank(message = "Powód jest wymagany.")
    @Column(name = "offense_reason", nullable = false)
    private String offenseReason;

    @NotNull(message = "Kwota jest wymagana.")
    @Positive(message = "Kwota musi być większa od zera.")
    @Column(name = "fine_amount", nullable = false)
    private BigDecimal fineAmount;

    @NotBlank(message = "Waluta jest wymagana.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    @Column(name = "administrative_fee", nullable = false)
    private BigDecimal administrativeFee;

    @NotNull(message = "Termin płatności jest wymagany.")
    @Column(name = "payment_due_date", nullable = false)
    private LocalDate paymentDueDate;

    @Column(nullable = false)
    private boolean paid;
}
