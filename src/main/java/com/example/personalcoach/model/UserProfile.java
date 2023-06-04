package com.example.personalcoach.model;


import jakarta.persistence.*;

@Entity
@Table(name = "user_profiles",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"phone"})
        })
public class UserProfile {
    @Id
    private Long id;

    private String urlPhoto;

    @Column(name = "phone")
    private String phoneNumber;

    private String address;

    private Long age;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Gender gender;

    private Boolean sending_sms;
    private Boolean mail_distribution;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;


}
