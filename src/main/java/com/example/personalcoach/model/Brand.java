package com.example.personalcoach.model;

import jakarta.persistence.*;

@Entity
@Table(name = "brand")
public class Brand {
    @Id
    @SequenceGenerator(name = "brand_sequence", sequenceName = "brand_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_sequence")
    private Long id;

    @Column(name = "name", unique = true)
    private String name;
    public Brand(){}
    public Brand(String name) {
        this.name = name;
    }

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
