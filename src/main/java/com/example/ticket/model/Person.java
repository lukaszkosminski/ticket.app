package com.example.ticket.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jdk.jfr.Enabled;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "job_type")
    @Enumerated(EnumType.STRING)
    private JobType jobType;
    @NotNull(message = "Email jest wymagany")
    @Email(message = "niepoprawny format emailu")
    private String email;
    @NotNull(message = "numer telefonu jesyt wymagany")
    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{3}$", message = "numer telefonu musi być w formacie XXX-XXX-XXX")
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    private String company;
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @EqualsAndHashCode.Exclude
    @OneToMany
    private Set<Ticket> tickets = new HashSet<>();

}
