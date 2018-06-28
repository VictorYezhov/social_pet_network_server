package com.server_for_spn.service;

import com.server_for_spn.entity.User;

import java.util.List;

public interface UserService {
    void save(User user);

    List<User> findAll();

    User findOne(Long id);

    void delete(Long  id);

    void update(User user);
}
