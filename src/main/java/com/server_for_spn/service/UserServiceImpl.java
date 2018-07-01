package com.server_for_spn.service;

import com.server_for_spn.dao.UserDAO;
import com.server_for_spn.dto.RegistrationForm;
import com.server_for_spn.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private  UserDAO userDAO;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CityService cityService;

    @Override
    public void save(User user) {
        userDAO.save(user);
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public User findOne(Long id) {
        return userDAO.getOne(id);
    }

    @Override
    public void delete(Long id) {
        userDAO.deleteById(id);
    }

    @Override
    public void update(User user) {
        userDAO.save(user);
    }

    @Override
    public String registration(RegistrationForm registrationForm) {


        registrationForm.setPassword(bCryptPasswordEncoder.encode(registrationForm.getPassword()));
        if(checkExistence(registrationForm.getEmail(), registrationForm.getPassword())){
            return "User with such email and password already exists";
        }
        User user = new User();
        user.setName(registrationForm.getName());
        user.setFamilyName(registrationForm.getFamilyName());
        user.setEmail(registrationForm.getEmail());
        user.setPassword(registrationForm.getPassword());
        user.setPhoneNumber(registrationForm.getPhoneNumber());
        user.setAddress("Not set yet");




        return null;
    }

    @Override
    public boolean checkExistence(String email, String password) {
        return userDAO.findFirstByEmailAndPassword(email, password) != null;
    }
}
