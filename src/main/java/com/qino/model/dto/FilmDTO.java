package com.qino.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.Year;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FilmDTO extends BaseDTO {
    private Long id;
    @NonNull
    private String title;
    private Year releaseYear;
    @NonNull
    private String description;
    private Integer budget;
    private Integer grosses;
    @NonNull
    private Integer duration;
    private Long votes;
    private Float rating;
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
