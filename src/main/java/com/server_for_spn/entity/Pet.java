package com.server_for_spn.entity;

import com.server_for_spn.enums.PetType;

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




}
