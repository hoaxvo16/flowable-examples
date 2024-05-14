package com.hoaxvo.flowable.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "my_entity")
public class MyEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
}
