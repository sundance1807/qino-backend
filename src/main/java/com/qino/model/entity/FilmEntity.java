package com.qino.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Year;
import java.util.Set;

@Entity
@Data
@Table(name = "films")
@EqualsAndHashCode(callSuper = true)
public class FilmEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false, length = 155)
    private String title;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "films_2_genres",
        joinColumns = @JoinColumn(name = "film_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "genre_id", nullable = false))
    private Set<GenreEntity> genres;
    @Column(name = "release_year", nullable = false)
    private Year releaseYear;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "films_2_persons",
    joinColumns = @JoinColumn(name = "film_id", nullable = false),
    inverseJoinColumns = @JoinColumn(name = "person_id", nullable = false))
    private Set<PersonEntity> directors;
    private Long votes;
    private Float rating;
}
