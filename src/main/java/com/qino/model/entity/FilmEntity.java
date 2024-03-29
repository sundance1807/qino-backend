package com.qino.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Year;
import java.util.HashSet;
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
    @Column(name = "release_year")
    private Year releaseYear;
    @Column(name = "description", nullable = false, length = 1000)
    private String description = "";
    @Column(name= "budget")
    private Integer budget = 0;
    @Column(name= "box_office")
    private Integer boxOffice = 0;
    @Column(name = "duration", nullable = false)
    private Integer duration = 0;
    @Column(name = "votes")
    private Long votes = 0L;
    @Column(name = "rating")
    private Float rating = 0F;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "films_2_genres",
        joinColumns = @JoinColumn(name = "film_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "genre_id", nullable = false))
    private Set<GenreEntity> genres = new HashSet<>();
    
    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(name = "films_2_production_members",
    joinColumns = @JoinColumn(name = "film_id", nullable = false),
    inverseJoinColumns = @JoinColumn(name = "production_member_id", nullable = false))
    private Set<ProductionMemberEntity> productionMembers = new HashSet<>();
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "films_2_actors",
        joinColumns = @JoinColumn(name = "film_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "person_id", nullable = false))
    private Set<PersonEntity> actors = new HashSet<>();
}
