package com.example.ticket.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

//    @Lob
//    private byte[] attachment;
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
}
