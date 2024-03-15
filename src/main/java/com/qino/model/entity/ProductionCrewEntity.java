package com.qino.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "production_crews")
@EqualsAndHashCode(callSuper = true)
public class ProductionCrewEntity extends BaseEntity {
    private Long id;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "films_2_directors",
        joinColumns = @JoinColumn(name = "film_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "person_id", nullable = false))
    private Set<PersonEntity> directors = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "films_2_producers",
        joinColumns = @JoinColumn(name = "film_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "person_id", nullable = false))
    private Set<PersonEntity> producers = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "films_2_writers",
        joinColumns = @JoinColumn(name = "film_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "person_id", nullable = false))
    private Set<PersonEntity> writers = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "films_2_composers",
        joinColumns = @JoinColumn(name = "film_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "person_id", nullable = false))
    private Set<PersonEntity> composers = new HashSet<>();
}
