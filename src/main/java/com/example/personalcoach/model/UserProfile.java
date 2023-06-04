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

    public UserProfile(){}
    public UserProfile(User user) {
        this.user = user;
        this.id = user.getId();
        this.gender = Gender.MALE;
        this.sending_sms = false;
        this.mail_distribution = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Boolean getSending_sms() {
        return sending_sms;
    }

    public void setSending_sms(Boolean sending_sms) {
        this.sending_sms = sending_sms;
    }

    public Boolean getMail_distribution() {
        return mail_distribution;
    }

    public void setMail_distribution(Boolean mail_distribution) {
        this.mail_distribution = mail_distribution;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
