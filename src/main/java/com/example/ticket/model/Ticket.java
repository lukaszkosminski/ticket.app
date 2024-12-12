package com.example.ticket.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Sygnatura jest wymagana.")
    @Column(nullable = false, unique = true)
    private String signature;

    @NotNull(message = "Data wykroczenia jest wymagana.")
    @Column(nullable = false)
    private LocalDate violationDate;

    @NotBlank(message = "Powód jest wymagany.")
    @Column(nullable = false)
    private String reason;

    @NotNull(message = "Kwota jest wymagana.")
    @Positive(message = "Kwota musi być większa od zera.")
    @Column(nullable = false)
    private Double amount;

    @NotBlank(message = "Waluta jest wymagana.")
    @Column(nullable = false)
    private Currency currency;

    @Column(nullable = false)
    private Double administrativeFee = 100.0;

    @NotNull(message = "Termin płatności jest wymagany.")
    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private String status = "do zapłaty";

    private String attachmentPath;

    public String generateAttachmentPath() {
        return "uploads/" + signature + ".pdf";
    }

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
}
