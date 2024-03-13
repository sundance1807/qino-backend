package com.qino.model.dto;

import com.qino.model.entity.FilmEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.Year;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class FilmPreviewDTO {

    private Long id;
    @NonNull
    private String title;
    @NonNull
    private Year releaseYear;
    @NonNull
    private Integer duration;
    @NonNull
    private Set<GenreDTO> genres = new HashSet<>();
    @NonNull
    private Set<PersonDTO> directors = new HashSet<>();
    @NonNull
    private Set<PersonDTO> actors = new HashSet<>();
    @NonNull
    private Float rating;
    @NonNull
    private Long votes;

    public FilmPreviewDTO(FilmEntity filmEntity) {
        this.id = filmEntity.getId();
        this.title = filmEntity.getTitle();
        this.releaseYear = filmEntity.getReleaseYear();
        this.duration = filmEntity.getDuration();
        this.genres = filmEntity.getGenres()
            .stream()
            .map(GenreDTO::new)
            .collect(Collectors.toSet());
        this.directors = filmEntity.getDirectors()
            .stream()
            .map(PersonDTO::new)
            .collect(Collectors.toSet());
        this.actors = filmEntity.getActors()
            .stream()
            .map(PersonDTO::new)
            .collect(Collectors.toSet());
        this.rating = filmEntity.getRating();
        this.votes = filmEntity.getVotes();
    }
}
