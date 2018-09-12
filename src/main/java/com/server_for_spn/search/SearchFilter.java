package com.server_for_spn.search;

import com.server_for_spn.dto.SearchForm;
import com.server_for_spn.entity.Pet;
import com.server_for_spn.entity.User;

import com.server_for_spn.entity.UserState;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor on 12.09.2018.
 */
@Service
@Scope(value = "prototype")
public class SearchFilter {


    private List<User> filtered;
    private List<User> toRemove;
    private SearchForm searchForm;

    public SearchFilter() {
        this.filtered = new ArrayList<>();
        toRemove = new ArrayList<>();
    }

    public List<User> filter(SearchForm searchForm, List<com.server_for_spn.entity.User> startingList){
        filtered.addAll(startingList);
        this.searchForm = searchForm;

        filterByAnimalType()
                .end()
                .filterByBreed()
                .end()
                .filterByPetName()
                .end()
                .filterByOwnersName().end();


        return filtered;
    }

    private SearchFilter filterByBreed(){
        if(searchForm.getBreed() == null || filtered.isEmpty()){
            return this;
        }
        for (User user: filtered) {
            Pet p = getPetFromListById(user);
            if(!p.getBreed().getName().toLowerCase().contains(searchForm.getBreed().toLowerCase())){
                toRemove.add(user);
            }
        }
        return this;
    }
    private SearchFilter filterByPetName(){
        if(searchForm.getName() == null || filtered.isEmpty()){
            return this;
        }
        for (User user: filtered) {
            Pet p = getPetFromListById(user);
            if(!p.getName().toLowerCase().contains(searchForm.getName().toLowerCase())){
                toRemove.add(user);
            }
        }
        return this;
    }
    private SearchFilter filterByOwnersName(){
        if(searchForm.getOwnersName() == null || filtered.isEmpty()){
            return this;
        }
        StringBuilder builder = new StringBuilder();
        for(User user: filtered){
            builder.append(user.getName());
            builder.append(user.getFamilyName());
            if(!builder.toString().toLowerCase().contains(searchForm.getOwnersName().toLowerCase())){
                System.out.println("User "+ user.getName()+" "+user.getFamilyName());
                toRemove.add(user);
            }
            builder.delete(0, builder.length());
        }
        return this;
    }

    private SearchFilter filterByAnimalType(){
        if(searchForm.getPetType() == null || filtered.isEmpty()){
            return this;
        }
        for(User user: filtered){
            Pet p = getPetFromListById(user);
            if(!p.getBreed().getType().equals(searchForm.getPetType())){
                toRemove.add(user);
            }
        }
        return this;
    }

    private SearchFilter end(){
        filtered.removeAll(toRemove);
        toRemove.clear();
        return this;
    }
    private Pet getPetFromListById(User user){
        UserState userState = user.getUserState();
        for (Pet p:
             user.getPetList()) {
            if(p.getId().equals(userState.getCurrentPetChoose())){
                return p;
            }
        }
        throw  new RuntimeException();
    }

}
