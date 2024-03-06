package com.qino.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "film_descriptions")
@EqualsAndHashCode(callSuper = true)
public class FilmDescriptionEntity extends BaseEntity {
    @Id
    private Long id;
    @Column(name = "description", nullable = false, length = 1000)
    private String description;
}
