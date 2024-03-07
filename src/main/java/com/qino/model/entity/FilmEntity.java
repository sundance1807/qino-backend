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
    private Year releaseYear;
    @Column(name = "description", nullable = false, length = 1000)
    private String description;
    private Integer budget;
    private Integer grosses;
    @Column(name = "duration", nullable = false)
    private Integer duration;
    @Column(name = "votes")
    private Long votes;
    @Column(name = "rating")
    private Float rating;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "films_2_genres",
        joinColumns = @JoinColumn(name = "film_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "genre_id", nullable = false))
    private Set<GenreEntity> genres;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "films_2_directors",
        joinColumns = @JoinColumn(name = "film_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "person_id", nullable = false))
    private Set<PersonEntity> directors;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "films_2_producers",
        joinColumns = @JoinColumn(name = "film_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "person_id", nullable = false))
    private Set<PersonEntity> producers;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "films_2_writers",
        joinColumns = @JoinColumn(name = "film_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "person_id", nullable = false))
    private Set<PersonEntity> writers;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "films_2_composers",
        joinColumns = @JoinColumn(name = "film_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "person_id", nullable = false))
    private Set<PersonEntity> composers;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "films_2_actors",
        joinColumns = @JoinColumn(name = "film_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "person_id", nullable = false))
    private Set<PersonEntity> actors;
}
