package com.server_for_spn.entity;

import javax.persistence.*;

/**
 * Created by Victor on 03.09.2018.
 */
@Entity
public class LostPet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private City city;

    private String subLocality;

    private Long lostPetId;

    public Long getLostPetId() {
        return lostPetId;
    }

    public void setLostPetId(Long lostPetId) {
        this.lostPetId = lostPetId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getSubLocality() {
        return subLocality;
    }

    public void setSubLocality(String subLocality) {
        this.subLocality = subLocality;
    }
}
