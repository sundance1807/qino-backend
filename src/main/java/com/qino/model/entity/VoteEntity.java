//package com.qino.model.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//
//@Entity
//@Data
//@Table(name = "votes")
//@EqualsAndHashCode(callSuper = true)
//public class VoteEntity extends BaseEntity{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @Column(name = "title", nullable = false)
//    private String title;
//    @Column(name = "description", length = 1000, nullable = false)
//    private String description;
//    @Column(name = "vote", length = 3, nullable = false)
//    private byte vote;
//}
