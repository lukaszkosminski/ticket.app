package com.example.ticket.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jdk.jfr.Enabled;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private JobType jobType;
    @NotNull(message = "Email jest wymagany")
    @Email(message = "niepoprawny format emailu")
    private String email;
    @NotNull(message = "numer telefonu jesyt wymagany")
    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{3}$", message = "numer telefonu musi być w formacie XXX-XXX-XXX")
    private String phoneNumber;
    private String company;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ticket> tickets = new HashSet<>();

}
