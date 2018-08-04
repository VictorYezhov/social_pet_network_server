package com.server_for_spn.service;

import com.server_for_spn.dao.UserDAO;
import com.server_for_spn.dto.PetDTO;
import com.server_for_spn.dto.RegistrationForm;
import com.server_for_spn.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private  UserDAO userDAO;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CityService cityService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private PetService petService;

    @Autowired
    private WeightService weightService;


    @Override
    public void save(User user) {
        userDAO.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userDAO.findFirstByEmail(email);
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


        byte[] decodedBytesOfPassword = Base64.getDecoder().decode(registrationForm.getPassword());
        byte[] decodedBytesOfEmail = Base64.getDecoder().decode(registrationForm.getEmail());
        String password = new String(decodedBytesOfPassword);
        String email = new String(decodedBytesOfEmail);

        registrationForm.setPassword(password);
        registrationForm.setEmail(email);

        registrationForm.setPassword(bCryptPasswordEncoder.encode(registrationForm.getPassword()));

        if(checkExistence(registrationForm.getEmail(), registrationForm.getPassword())){

//            User user = userDAO.findFirstByEmail(registrationForm.getEmail());
//            List<Pet> petList = user.getPetList();
//
//            for (Pet p:
//                 petList) {
//                System.out.println(p.getId());
//            }
//
//            Pet pet = new Pet(registrationForm.getPet());
//            petService.save(pet);
//            Pet petReadyToSave = petService.findOneByTagNumber(registrationForm.getPet().getTagNumber());
//            petReadyToSave.setUser(user);
//
//            petList.add(petReadyToSave);
//            petService.update(petReadyToSave);
//            userDAO.save(user);

            //TODO do smth

            return "User with such email and password already exists";
        }

        User user = new User();
        user.setName(registrationForm.getName());
        user.setFamilyName(registrationForm.getSurname());
        user.setEmail(registrationForm.getEmail());
        user.setPassword(registrationForm.getPassword());
        user.setPhoneNumber(registrationForm.getPhone());
        user.setAddress("Not set yet");

        Country country  = countryService.findOne(registrationForm.getCity().getCountry().getId());
        if(country == null){
            return "No such country exists\n";
        }
        City city = cityService.findByNameAndCountry(registrationForm.getCity().getName(), country);

        if(city == null){
            city = new City();
            city.setName(registrationForm.getCity().getName());
            city.setCountry(country);
            cityService.save(city);
        }
        user.setCity(city);

        Weight weight= weightService.findOneByMassAndMassUnit(registrationForm.getPet().getWeight().getMass(),
                registrationForm.getPet().getWeight().getMassUnit());
        if(weight == null){
            weight = new Weight(registrationForm.getPet().getWeight());
            weightService.save(weight);
        }



        Pet pet = new Pet(registrationForm.getPet());
        pet.setWeight(weight);
        List<Pet> petsOfWeight = weight.getPetList();
        petsOfWeight.add(pet);
        weightService.update(weight);
        userDAO.save(user);
        pet.setUser(user);
        user.getPetList().add(pet);
        petService.save(pet);
        userDAO.save(user);
        weightService.update(weight);


        return "You have been registered!";
    }

    @Override
    public boolean checkExistence(String email, String password) {
        return userDAO.findFirstByEmailAndPassword(email, password) != null;
    }




}
