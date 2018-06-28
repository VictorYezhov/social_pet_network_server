package com.server_for_spn.entity;

import com.server_for_spn.enums.PetType;

import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Victor on 28.06.2018.
 */
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;


    @Enumerated
    private PetType type;

}
