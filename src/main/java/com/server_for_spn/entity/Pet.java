package com.server_for_spn.entity;

import com.server_for_spn.dto.PetDTO;
import com.server_for_spn.enums.Attitude;
import com.server_for_spn.enums.PetType;
import com.server_for_spn.enums.Sex;

import javax.persistence.*;

/**
 * Created by Victor on 28.06.2018.
 */
@Entity
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long age;
    @ManyToOne
    @JoinColumn(name = "breed_id")
    private Breed breed;
    @Enumerated
    private Sex sex;

    @ManyToOne
    @JoinColumn(name = "weight_id")
    private Weight weight;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String tagNumber;

    @Enumerated
    private  Attitude attitude;

    public Pet() {
    }

    public Pet(PetDTO petDTO) {
        this.name = petDTO.getName();
        this.age = petDTO.getAge();
        this.breed = petDTO.getBreed();
        this.sex = petDTO.getSex();
        //this.weight = petDTO.getWeight();
        //this.user = user;
        this.tagNumber = petDTO.getTagNumber();
        this.attitude = petDTO.getAttitude();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    public String getTagNumber() {
        return tagNumber;
    }

    public void setTagNumber(String tagNumber) {
        this.tagNumber = tagNumber;
    }

    public Attitude getAttitude() {
        return attitude;
    }

    public void setAttitude(Attitude attitude) {
        this.attitude = attitude;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
