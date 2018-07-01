package com.server_for_spn.dao;

import com.server_for_spn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDAO extends JpaRepository<User, Long> {

    User findFirstByEmailAndPassword(String email, String password);



}
