//package com.qino.model.entity;
//
//import com.qino.model.entity.cast.ComposerEntity;
//import com.qino.model.entity.cast.DirectorEntity;
//import com.qino.model.entity.cast.WriterEntity;
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//@Data
//@Table(name = "films")
//@EqualsAndHashCode(callSuper = true)
//public class FilmEntity extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @Column(name = "title", nullable = false, length = 155)
//    private String title;
//    @Column(name = "tagline", nullable = false, length = 155)
//    private String tagline;
//    @Column(name = "genre", nullable = false)
//    private Set<GenreEntity> genre;
//    @Column(name = "country", nullable = false, length = 155)
//    private String country;
////    @Column(name = "directors", nullable = false)
////    private Set<DirectorEntity> directors = new HashSet<>();
////    @Column(name = "writers", nullable = false)
////    private Set<WriterEntity> writers = new HashSet<>();
////    @Column(name = "composers", nullable = false)
//    private Set<ComposerEntity> composers = new HashSet<>();
////    @Column(name = "votes", nullable = false)
////    private Set<VoteEntity> votes = new HashSet<>();
//}
