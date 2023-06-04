package com.example.personalcoach.model;

import jakarta.persistence.*;

@Entity
@Table(name = "basket")
public class Basket{
    @Id
    @SequenceGenerator(name = "basket_item_sequence", sequenceName = "basket_item_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "basket_item_sequence")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "counter")
    private Long counter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }
}
