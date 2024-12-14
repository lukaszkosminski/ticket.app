package com.example.ticket.model.dto;

import com.example.ticket.model.Currency;
import com.example.ticket.model.Person;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class GetTicketDTO {

    private Long id;

    private String signature;

    private LocalDate offenseDate;

    private String offenseReason;

    private BigDecimal fineAmount;

    private Currency currency;

    private BigDecimal administrativeFee;

    private LocalDate paymentDueDate;

    private boolean paid;

//    @Lob
//    private byte[] attachment;

    private Person person;
}
