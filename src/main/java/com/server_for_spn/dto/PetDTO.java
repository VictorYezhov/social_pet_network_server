package com.server_for_spn.dto;

import com.server_for_spn.entity.Breed;
import com.server_for_spn.entity.Pet;
import com.server_for_spn.enums.Attitude;
import com.server_for_spn.enums.Sex;

public class PetDTO {
    private Long id;
    private String name;
    private Breed breed;
    private Long age;
    private Sex sex;
    private String tagNumber;
    private Double weight;
    private Attitude attitude;

    public PetDTO(){

    }

    public PetDTO(String name, Breed breed, Long age, Sex sex, String tagNumber, Double weight, Attitude attitude) {
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.sex = sex;
        this.tagNumber = tagNumber;
        this.weight = weight;
        this.attitude = attitude;
    }

    public PetDTO(Pet pet){
        this.id = pet.getId();
        this.name = pet.getName();
        this.breed = pet.getBreed();
        this.age = pet.getAge();
        this.sex = pet.getSex();
        this.tagNumber = pet.getTagNumber();
        this.weight = pet.getWeight().getMass();
        this.attitude = pet.getAttitude();

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

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getTagNumber() {
        return tagNumber;
    }

    public void setTagNumber(String tagNumber) {
        this.tagNumber = tagNumber;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Attitude getAttitude() {
        return attitude;
    }

    public void setAttitude(Attitude attitude) {
        this.attitude = attitude;
    }
}

