package com.server_for_spn.dao;

import com.server_for_spn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDAO extends JpaRepository<User, Long> {

    @Query(value = "SELECT * from user WHERE user.email=:email and user.password=:password",  nativeQuery = true)
    User findFirstByEmailAndPassword(@Param("email") String email, @Param("password") String password);


    User findFirstByEmail(String email);



}
