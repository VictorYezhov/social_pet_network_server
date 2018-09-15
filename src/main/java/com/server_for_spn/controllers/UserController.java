package com.server_for_spn.controllers;

import com.server_for_spn.dto.*;
import com.server_for_spn.entity.*;
import com.server_for_spn.search.SearchFilter;
import com.server_for_spn.service.CityService;
import com.server_for_spn.service.CountryService;
import com.server_for_spn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Victor on 01.07.2018.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private SearchFilter searchFilter;


    @Autowired
    private CityService  cityService;
    /**
     * Registration of new Users
     * @param registrationForm
     * @return
     */
    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody RegistrationForm registrationForm){
        if(registrationForm == null)
            return new ResponseEntity<>( "registrationForm is null", HttpStatus.BAD_REQUEST);;
        userService.registration(registrationForm);
        return new ResponseEntity<>( "OK", HttpStatus.ACCEPTED);
    }


    @GetMapping("/loginFailed")
    public String loginFail(){
        return "login fail";
    }


    /**
     * We must update FCM token every time user login
     * !! SO THIS METHOD MUST BE CALLED EVERY LOGIN ACTION !!!
     * UpdateTokenForm contains Token and userId ENCODED IN BASE64 FORMAT WITH NO_WRAP flag :
     * Encoder flag bit to omit all line terminators (i.e., the output
     * will be on one long line).
     * So we need to decode it first
     * @param updateTokenForm
     * @param authentication
     * @return
     */
    @PostMapping("/updateUsersToken")
    public ResponseEntity<String> updateToken(@RequestBody UpdateTokenForm updateTokenForm, Authentication authentication){

        updateTokenForm.setId(new String(Base64.getDecoder().decode(updateTokenForm.getId())));
        updateTokenForm.setToken(new String(Base64.getDecoder().decode(updateTokenForm.getToken())));
        User u = userService.findOne(Long.decode(updateTokenForm.getId()));
        System.out.println("NEW TOKEN: "+updateTokenForm.getToken());
        if(!authentication.getPrincipal().toString().equals(u.getEmail())){
            return new ResponseEntity<>("CONFLICT", HttpStatus.CONFLICT);
        }

        u.setFcmToken(updateTokenForm.getToken());
        userService.update(u);
        return new ResponseEntity<>( "OK", HttpStatus.ACCEPTED);
    }

    /**
     * Getting all needed information after user login.
     * @param id
     * @param authentication
     * @return
     */
    @PostMapping("/getAllInformationAboutUserAndPets")
    public ResponseEntity<InformationOfUserAndHisPet> sendInfoToClient(@RequestBody Long id, Authentication authentication){
        InformationOfUserAndHisPet info = new InformationOfUserAndHisPet();
        User u = userService.findOne(id);
        if(!authentication.getPrincipal().toString().equals(u.getEmail())){
            return new ResponseEntity<>(info, HttpStatus.CONFLICT);

        }
        u.getUserState().setLastActiveTime(new Timestamp(System.currentTimeMillis()));

        info.setName(u.getName());
        info.setSurname(u.getFamilyName());
        info.setPhone(u.getPhoneNumber());

        CityDTO cityDTO = new CityDTO(u.getCity());

        info.setCity(cityDTO);

        List<PetDTO> petDTOList = new ArrayList<>();

        for (Pet pet: u.getPetList()) {
            PetDTO petDTO = new PetDTO(pet);
            petDTOList.add(petDTO);
        }

        info.setPet(petDTOList);

        return new ResponseEntity<>(info, HttpStatus.ACCEPTED);
    }



    @PostMapping("/online")
    public ResponseEntity<String>  online(@RequestParam("id")Long id,@RequestParam("time") String time,
                                          Authentication authentication){

        User u = userService.findOne(id);
        if(u.getEmail().equals(authentication.getPrincipal().toString())){
            Timestamp t = Timestamp.valueOf(time);
            System.out.println(t.toString());
            u.getUserState().setLastActiveTime(t);
            userService.save(u);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        }
        return new ResponseEntity<>("BAD", HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/getInformationOfAnotherUser")
    public ResponseEntity<UserInfo> sendInfoAboutAnotherUser(@RequestParam("id") Long id){

        User user = userService.findOne(id);
        UserInfo userInfo = new UserInfo(user);

        return new ResponseEntity<UserInfo>(userInfo, HttpStatus.ACCEPTED);
    }

    @PostMapping("/getAllContactsOfCurrentUser")
    public List<Contact> getAllContactsOfCurrentUser(@RequestParam("id") Long id, Authentication authentication){
        List<Contact> contacts = new ArrayList<>();
        User user = userService.findOne(id);
        if(!authentication.getPrincipal().toString().equals(user.getEmail())){
            return null;
        }
        List<Friends> friends = user.getFriends();
        for (Friends f: friends) {
            Contact c;
            if(!f.getSide1().getId().equals(id)){
                c = new Contact(f.getSide1());
                c.setOnlineTime(f.getSide1().getUserState().getLastActiveTime());
            }else{
                c = new Contact(f.getSide2());
                c.setOnlineTime(f.getSide2().getUserState().getLastActiveTime());
            }



            try {
                Message m = f.getMessages().get(f.getMessages().size() - 1);
                c.setLastMessage(m.getMessageText());
                c.setTimestamp(m.getTimestamp());
            }catch (IndexOutOfBoundsException e){
                c.setLastMessage("no messages");
            }

            contacts.add(c);
        }

        return contacts;
    }

    @PostMapping("/checkIfUserIsOnline")
    public String sendUserLastActiveTime(@RequestParam("id") Long id){
        User user = userService.findOne(id);
        String time = user.getUserState().getLastActiveTime().toString();
        String time1 = time.replaceAll("\\s", "%");
        return time1.replaceAll(":","\\^");
    }

    @PostMapping("/getNumberOfFriendsOfAnotherUser")
    public Integer getNumberOfFriendsOfAnotherUser(@RequestParam("user") Long id){
        User user = userService.findOne(id);
        return user.getFriends().size();
    }

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody SearchForm searchForm){
        System.out.println(searchForm);
        Country country = countryService.findByName(searchForm.getUserCountry());
        if(country == null){
            return new ResponseEntity<>("Wrong Country name",HttpStatus.BAD_REQUEST);
        }
        City city = cityService.findByNameAndCountry(searchForm.getUserCity(), country);
        if(city == null){
            return  new ResponseEntity<>("Wrong city name",HttpStatus.BAD_REQUEST);
        }
        List<User> users = userService.findAllByCity(city);
        if(users.isEmpty()){
            return new ResponseEntity<>("No  users in This city",HttpStatus.ACCEPTED);
        }
        List<User> filtred = searchFilter.filter(searchForm, users);

        Set<FriendInfo> friendsInfo = new HashSet<>();
        FriendInfo info;
        for (User u : filtred) {
            info = new FriendInfo();
            info.setName(u.getName());
            info.setSurname(u.getFamilyName());
            info.setId(u.getId());
            info.setLastActiveTime(u.getUserState().getLastActiveTime());
            Pet pet = getCurrentPetChoise(u);
            if(pet != null) {
                info.setPetName(pet.getName());
                info.setPetBreedName(pet.getBreed().getName());
            }
            friendsInfo.add(info);
        }
        System.out.println(friendsInfo);
        return new ResponseEntity<>(friendsInfo, HttpStatus.OK);
    }


    private Pet getCurrentPetChoise(User user){

        for (Pet p:
                user.getPetList()) {
            if (p.getId().equals(user.getUserState().getCurrentPetChoose())) {
                return p;
            }
        }
        return null;
    }


}
