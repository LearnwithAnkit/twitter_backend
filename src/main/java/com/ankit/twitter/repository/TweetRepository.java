package com.ankit.twitter.repository;

import com.ankit.twitter.model.Tweets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweets,Long> {


    public List<Tweets> findbyisTweetTrueOrderByCreatedAtDesc();
    public List<Tweets> findByisTweetContains

}
