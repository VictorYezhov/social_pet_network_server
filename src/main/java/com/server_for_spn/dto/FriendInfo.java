package com.server_for_spn.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

/**
 * Created by Victor on 14.08.2018.
 */
public class FriendInfo {

    private Long id;
    private String name;
    private String surname;
    private String petName;
    private String petBreedName;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Timestamp lastActiveTime;


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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetBreedName() {
        return petBreedName;
    }

    public void setPetBreedName(String petBreedName) {
        this.petBreedName = petBreedName;
    }

    public Timestamp getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(Timestamp lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FriendInfo)) return false;

        FriendInfo that = (FriendInfo) o;

        if (!getId().equals(that.getId())) return false;
        if (!getName().equals(that.getName())) return false;
        if (!getSurname().equals(that.getSurname())) return false;
        if (!getPetName().equals(that.getPetName())) return false;
        if (!getPetBreedName().equals(that.getPetBreedName())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getSurname().hashCode();
        result = 31 * result + getPetName().hashCode();
        result = 31 * result + getPetBreedName().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "\n\nFriendInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", petName='" + petName + '\'' +
                ", petBreedName='" + petBreedName + '\'' +
                ", lastActiveTime=" + lastActiveTime +
                '}';
    }
}
