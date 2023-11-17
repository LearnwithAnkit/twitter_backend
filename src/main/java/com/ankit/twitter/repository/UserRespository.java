package com.ankit.twitter.repository;

import com.ankit.twitter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRespository extends JpaRepository<User,Long> {
    public User findByEmail(String email);
    @Query("SELECT DISTINCT u FROM User u WHERE u.fullName like %:query% or u.email like %:query% ")
    public List<User> searchUser(@Param("query") String query);

}
