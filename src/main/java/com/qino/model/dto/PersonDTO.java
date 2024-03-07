package com.qino.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PersonDTO extends BaseDTO {
    private String firstName;
    private String secondName;
    private LocalDate dateOfBirth;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int age;
}
