package com.qino.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.Year;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
public class FilmResponseDTO {

    private Long id;
    @NonNull
    private String title;
    private Year releaseYear;
    @NonNull
    private String description;
    private String budget;
    private String grosses;
    private String duration;
    private Long votes;
    private Float rating;
    @NonNull
    private Set<GenreDTO> genres = new HashSet<>();
    @NonNull
    private Map<String, Set<ProductionMemberDTO>> productionMembers;
    @NonNull
    private Set<PersonDTO> actors = new HashSet<>();
}
