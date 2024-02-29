package com.qino.model.entity.cast;

import com.qino.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@Entity
@Table(name = "directors")
@EqualsAndHashCode(callSuper = true)
public class DirectorEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "second_name", nullable = false)
    private String secondName;
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(name = "age", nullable = false)
    private short age;
}
