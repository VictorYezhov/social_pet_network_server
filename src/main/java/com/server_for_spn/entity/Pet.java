package com.server_for_spn.entity;

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
    private int age;
    @ManyToOne
    @JoinColumn(name = "breed_id")
    private Breed breed;
    @Enumerated
    private Sex sex;

    @ManyToOne
    @JoinColumn(name = "weight_id")
    private Weight weight;

    private String tagNuber;

    @Enumerated
    private  Attitude attitude;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
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

    public String getTagNuber() {
        return tagNuber;
    }

    public void setTagNuber(String tagNuber) {
        this.tagNuber = tagNuber;
    }

    public Attitude getAttitude() {
        return attitude;
    }

    public void setAttitude(Attitude attitude) {
        this.attitude = attitude;
    }
}
