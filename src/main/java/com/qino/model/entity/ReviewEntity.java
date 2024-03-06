package com.qino.model.entity;

import com.qino.model.ReviewType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "reviews")
@EqualsAndHashCode(callSuper = true)
public class ReviewEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "reviews_2_users",
        joinColumns = @JoinColumn(name = "review_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "user_id", nullable = false))
    private UserEntity user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "film_id")
    private FilmEntity film;
    @Enumerated(EnumType.STRING)
    private ReviewType type;
    @Column(name = "rate", length = 2)
    private Short rate;
    @Column(name = "title", length = 155)
    private String title;
    @Column(name = "description", length = 1000)
    private String description;
}
