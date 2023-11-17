package com.ankit.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "user")
public class User {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    private String fullName;
    private String location;
    private String password;
    private String email;
    private String website;
    private String birthDate;
    private String mobile;
    private String image;
    private String backgroundImage;
    private String bio;
    private boolean reqUser;
    private boolean loginWithGoogle;
    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Tweets> tweets =new ArrayList<>();
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Likes>  likes  =new ArrayList<>();
    @Embedded
    private Verification verification;
    @ManyToMany()
    @JsonIgnore
    List<User> followers =new ArrayList<>();
    @ManyToMany()
    @JsonIgnore
    List<User> followings =new ArrayList<>();



}
