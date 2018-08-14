package com.server_for_spn.entity;




import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor on 28.06.2018.
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Name;
    private String FamilyName;
    private String email;
    private String password;
    private String phoneNumber;
    private String Address;

    private String fcmToken;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "user")
    private UserState UserState;


    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Pet> petList;



    @OneToMany(mappedBy = "side1", fetch = FetchType.LAZY)
    private List<Friends> myFriends;

    @OneToMany(mappedBy = "side2", fetch = FetchType.LAZY)
    private List<Friends> meInFriends;





    public User() {
        petList = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFamilyName() {
        return FamilyName;
    }

    public void setFamilyName(String familyName) {
        FamilyName = familyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Pet> getPetList() {
        return petList;
    }

    public void setPetList(List<Pet> petList) {
        this.petList = petList;
    }

    public List<Friends> getMyFriends() {
        return myFriends;
    }

    public void setMyFriends(List<Friends> myFriends) {
        this.myFriends = myFriends;
    }

    public List<Friends> getMeInFriends() {
        return meInFriends;
    }

    public void setMeInFriends(List<Friends> meInFriends) {
        this.meInFriends = meInFriends;
    }


    public com.server_for_spn.entity.UserState getUserState() {
        return UserState;
    }

    public void setUserState(com.server_for_spn.entity.UserState userState) {
        UserState = userState;
    }

    public List<Friends> getFriends(){
        List<Friends> friends = new ArrayList<>();
        friends.addAll(myFriends);
        friends.addAll(meInFriends);
        return friends;
    }



}
