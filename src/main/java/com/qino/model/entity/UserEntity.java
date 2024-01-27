package com.qino.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "password")
    private String password;
//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(name = "users_2_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false))
//    private List<Role> roles = new ArrayList<>();
}
