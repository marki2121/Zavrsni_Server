package com.example.zavrsnirad.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

// Ova klasa predstavlja entitet korisnickog profila u bazi podataka
@Entity
@Table(name = "user_profile")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "address")
    private String address;
    @Column(name = "city")
    private String city;
    @Column(name = "zip_code")
    private String zipCode;
    @Column(name = "country")
    private String country;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "about_me")
    private String aboutMe;
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
