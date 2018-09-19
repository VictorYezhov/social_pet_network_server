package com.server_for_spn.service;

import com.server_for_spn.dto.Pair;
import com.server_for_spn.dto.RegistrationForm;
import com.server_for_spn.entity.City;
import com.server_for_spn.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    void save(User user);

    List<User> findAll();
    List<User> findAllByCity(City city);

    User findOne(Long id);

    void delete(Long  id);

    void update(User user);

    boolean checkExistenceById(Long Id);

    boolean checkExistence(String email, String password);

    Pair<String, User> registration(RegistrationForm registrationForm);
    Pair<String, User> registration(RegistrationForm registrationForm, MultipartFile img);

    User findByEmail(String email);


}
