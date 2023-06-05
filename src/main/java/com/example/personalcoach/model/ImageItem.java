package com.example.personalcoach.model;


import jakarta.persistence.*;

@Entity
@Table(name = "item_image")
public class ImageItem {
    @Id
    @SequenceGenerator(name = "item_image_sequence", sequenceName = "item_image_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_image_sequence")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item itemId;

    @Column(name = "url")
    private String url;


    public ImageItem(){}

    public ImageItem(Item itemId, String url) {
        this.itemId = itemId;
        this.url = url;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public Item getItemId() {
        return itemId;
    }

    public void setItemId(Item itemId) {
        this.itemId = itemId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}