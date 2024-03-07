package com.qino.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qino.model.entity.PersonEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
    private Long id;
    private String firstName;
    private String secondName;
    private LocalDate dateOfBirth;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int age;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime createdAt;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime updatedAt;

    public PersonDTO(PersonEntity personEntity) {
        this.id = personEntity.getId();
        this.firstName = personEntity.getFirstName();
        this.secondName = personEntity.getSecondName();
        this.dateOfBirth = personEntity.getDateOfBirth();
        this.age = personEntity.getAge();
    }
}
