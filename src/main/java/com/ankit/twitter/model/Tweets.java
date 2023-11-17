package com.ankit.twitter.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "tweets")
public class Tweets {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User user;
    private String       content;
    @OneToMany(mappedBy = "tweets",cascade = CascadeType.ALL)
    private List<Likes>  likes       =new ArrayList<>();
    @OneToMany
    private List<Tweets> replyTweets =new ArrayList<>();
    @ManyToMany
    private List<User> retweetUser=new ArrayList<>();
    @ManyToOne
    private Tweets reply;
    private String image;
    private String video;
    private boolean isReply;
    private boolean       isTweet;
    private LocalDateTime createdDate;

}
