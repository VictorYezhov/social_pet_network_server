package com.server_for_spn.dto;

import com.server_for_spn.entity.Breed;
import com.server_for_spn.entity.Pet;
import com.server_for_spn.enums.Attitude;
import com.server_for_spn.enums.Sex;

public class PetDTO {
    private Long id;
    private String name;
    private BreedDTO breed;
    private Long age;
    private Sex sex;
    private String tagNumber;
    private WeightDTO weight;
    private Attitude attitude;

    public PetDTO(){

    }

    public PetDTO(String name, BreedDTO breed, Long age, Sex sex, String tagNumber, WeightDTO weight, Attitude attitude) {
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

        BreedDTO breedDTO = new BreedDTO(pet.getBreed());

        this.breed = breedDTO;
        this.age = pet.getAge();
        this.sex = pet.getSex();
        this.tagNumber = pet.getTagNumber();

        WeightDTO weightDTO = new WeightDTO();
        weightDTO.setMass(pet.getWeight().getMass());
        weightDTO.setMassUnit(pet.getWeight().getMassUnit());

        this.weight = weightDTO;
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

    public BreedDTO getBreed() {
        return breed;
    }

    public void setBreed(BreedDTO breed) {
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

    public WeightDTO getWeight() {
        return weight;
    }

    public void setWeight(WeightDTO weight) {
        this.weight = weight;
    }

    public Attitude getAttitude() {
        return attitude;
    }

    public void setAttitude(Attitude attitude) {
        this.attitude = attitude;
    }
}

