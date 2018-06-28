package com.server_for_spn.entity;

import com.server_for_spn.enums.PetType;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Victor on 28.06.2018.
 */
@Entity
public class Breed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated
    private PetType type;

    private String name;

    @OneToMany(mappedBy = "breed", fetch = FetchType.LAZY)
    private List<Pet> petList;

}
