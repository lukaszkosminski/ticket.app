package com.example.ticket.model.dto.mapper;

import com.example.ticket.model.Person;
import com.example.ticket.model.dto.CreatePersonDTO;

public class PersonMapper {

    public static Person toEntity(CreatePersonDTO createPersonDto) {
        Person person = new Person();
        person.setFirstName(createPersonDto.getFirstName());
        person.setLastName(createPersonDto.getLastName());
        person.setJobType(createPersonDto.getJobType());
        person.setEmail(createPersonDto.getEmail());
        person.setPhoneNumber(createPersonDto.getPhoneNumber());
        person.setCompany(createPersonDto.getCompany());
        person.setActive(true);
        return person;
    }

}
