package com.server_for_spn.service;

import com.server_for_spn.dto.RegistrationForm;
import com.server_for_spn.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

public interface UserService {
    void save(User user);

    List<User> findAll();

    User findOne(Long id);

    void delete(Long  id);

    void update(User user);

    boolean checkExistenceById(Long Id);

    boolean checkExistence(String email, String password);

    String registration(RegistrationForm registrationForm);

    User findByEmail(String email);


}
