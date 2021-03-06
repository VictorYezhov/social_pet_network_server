package com.server_for_spn.service;

import com.server_for_spn.dao.UserDAO;
import com.server_for_spn.dao.UserStateDAO;
import com.server_for_spn.dto.Pair;
import com.server_for_spn.dto.PetDTO;
import com.server_for_spn.dto.RegistrationForm;
import com.server_for_spn.entity.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private  UserDAO userDAO;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CityService cityService;

    @Autowired
    private UserStateDAO userStateDAO;

    @Autowired
    private CountryService countryService;

    @Autowired
    private PetService petService;

    @Autowired
    private WeightService weightService;

    @Autowired
    private BreedService breedService;

    @Autowired
    private ImageSavingService imageSavingService;


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
    public boolean checkExistenceById(Long Id) {
        return userDAO.existsById(Id);
    }

    @Override
    public Pair<String, User> registration(RegistrationForm registrationForm) {


        byte[] decodedBytesOfPassword = Base64.getDecoder().decode(registrationForm.getPassword());
        byte[] decodedBytesOfEmail = Base64.getDecoder().decode(registrationForm.getEmail());
        String password = new String(decodedBytesOfPassword);
        String email = new String(decodedBytesOfEmail);

        registrationForm.setPassword(password);
        registrationForm.setEmail(email);

        registrationForm.setPassword(bCryptPasswordEncoder.encode(registrationForm.getPassword()));

        if(checkExistence(registrationForm.getEmail())){
            //TODO do smt
            return new Pair<>("User with such email already exists", null);
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
            return new Pair<>("No such country exists\n", null);
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

        Breed breed;

        if(registrationForm.getPet().getBreed().getId() == null){
            System.out.println("breed null");
            breed = new Breed();
            breed.setName(registrationForm.getPet().getBreed().getName());
            System.out.println("type " + registrationForm.getPet().getBreed().getType());
            breed.setType(registrationForm.getPet().getBreed().getType());
            List<Pet> pList = new ArrayList<>();
            breed.setPetList(pList);
            breedService.save(breed);
        }else{
            System.out.println("breed not null");
            breed = breedService.findOne(registrationForm.getPet().getBreed().getId());
        }

        Pet pet = new Pet(registrationForm.getPet());
        pet.setBreed(breed);
        pet.setWeight(weight);

        List<Pet> petsOfWeight = weight.getPetList();
        petsOfWeight.add(pet);
        weightService.update(weight);

        List<Pet> petsOfBreed = breed.getPetList();
        petsOfBreed.add(pet);
        breedService.update(breed);

        userDAO.save(user);
        pet.setUser(user);
        user.getPetList().add(pet);
        petService.save(pet);
        userDAO.save(user);
        weightService.update(weight);
        UserState userState = new UserState();
        userState.setUser(user);
        userState.setCurrentPetChoose(pet.getId());
        userStateDAO.save(userState);

        return new Pair<>("You have been registered!", user);
    }

    @Override
    public Pair<String, User> registration(RegistrationForm registrationForm, MultipartFile img) {
        Pair<String, User> pair = registration(registrationForm);
        if(pair.getValue() == null){
            return pair;
        }
        imageSavingService.savePhoto(img, pair.getValue(), true, null);

        return pair;
    }

    @Override
    public boolean checkExistence(String email) {
        return userDAO.findFirstByEmail(email) != null;
    }


    @Override
    public List<User> findAllByCity(City city) {
        return userDAO.findAllByCity(city);
    }








}
