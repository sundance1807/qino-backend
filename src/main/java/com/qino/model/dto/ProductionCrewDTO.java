package com.qino.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionCrewDTO {

    private Long id;
    @NonNull
    private Set<GenreDTO> genres = new HashSet<>();
    @NonNull
    private Set<PersonDTO> directors = new HashSet<>();
    @NonNull
    private Set<PersonDTO> producers = new HashSet<>();
    @NonNull
    private Set<PersonDTO> writers = new HashSet<>();
    @NonNull
    private Set<PersonDTO> composers = new HashSet<>();
    @NonNull
    private Set<PersonDTO> actors = new HashSet<>();
}
