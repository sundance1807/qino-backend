package com.qino.model.entity;

import com.qino.model.entity.cast.ComposerEntity;
import com.qino.model.entity.cast.DirectorEntity;
import com.qino.model.entity.cast.WriterEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    @Column(name = "tagline", nullable = false, length = 155)
    private String tagline;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "films_2_genres",
        joinColumns = @JoinColumn(name = "film_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "genre_id", nullable = false))
    private Set<GenreEntity> genres;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "films_2_directors",
        joinColumns = @JoinColumn(name = "film_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "director_id", nullable = false))
    private Set<DirectorEntity> directors;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "films_2_writers",
        joinColumns = @JoinColumn(name = "film_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "writer_id", nullable = false))
    private Set<WriterEntity> writers;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "films_2_composers",
        joinColumns = @JoinColumn(name = "film_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "composer_id", nullable = false))
    private Set<ComposerEntity> composers;

}
