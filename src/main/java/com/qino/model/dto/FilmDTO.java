package com.qino.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.Year;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FilmDTO extends BaseDTO {
    private Long id;
    private Year releaseYear;
    private String title;
    @NonNull
    private Set<GenreDTO> genres;
    private Long votes;
    private Float rating;
}
