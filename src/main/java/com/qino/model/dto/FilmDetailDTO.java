package com.qino.model.dto;

import com.qino.model.entity.PersonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FilmDetailDTO extends BaseDTO{
    private String description;
    @NonNull
    private Set<PersonEntity> directors;
    @NonNull
    private Set<PersonEntity> producers;
    @NonNull
    private Set<PersonEntity> writers;
    @NonNull
    private Set<PersonEntity> composers;
    @NonNull
    private Set<PersonEntity> actors;
    @NonNull
    private Integer budget;
    @NonNull
    private Integer grosses;
    private LocalDate premiereDate;
    @NonNull
    private Integer duration;
}
