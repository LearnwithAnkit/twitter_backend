package com.ankit.twitter.repository;

import com.ankit.twitter.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Likes,Long> {
    @Query("select l from Likes L where l.id= :UserId and l.tweets.id= :tweetId")
    public Likes isLikeExists(@Param("userId") Long userId,@Param("tweetId") Long tweetId);
   @Query("select l from Likes l where l.tweets.id= :tweetId")
    public List<Likes> findByTweetId(@Param("tweetId") String tweetId);
}
