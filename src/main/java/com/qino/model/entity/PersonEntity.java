package com.qino.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Table(name = "persons")
@EqualsAndHashCode(callSuper = true)
public class PersonEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "second_name", nullable = false)
    private String secondName;
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;
    @Column(name = "age", nullable = false)
    private int age;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "persons_2_careers",
        joinColumns = @JoinColumn(name = "person_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "career_id", nullable = false))
    private Set<CareerEntity> careers;
}
