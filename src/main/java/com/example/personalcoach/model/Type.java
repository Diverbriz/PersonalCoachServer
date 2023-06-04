package com.example.personalcoach.model;

import jakarta.persistence.*;


@Entity
@Table(name = "type")
public class Type {
    @Id
    @SequenceGenerator(name = "type_sequence", sequenceName = "type_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "type_sequence")
    private Long id;

    @Column(name = "name")
    private String name;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}