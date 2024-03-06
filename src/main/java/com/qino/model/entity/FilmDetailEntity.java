package com.qino.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Table(name = "film_details")
@EqualsAndHashCode(callSuper = true)
public class FilmDetailEntity extends BaseEntity {
    @Id
    private Long id;
    @Column(name = "description", nullable = false, length = 1000)
    private String description;
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
    private Integer budget;
    private Integer grosses;
    private LocalDate premiereDate;
    private Integer duration;
}
