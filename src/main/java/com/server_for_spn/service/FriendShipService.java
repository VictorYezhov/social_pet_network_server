package com.server_for_spn.service;

import com.server_for_spn.dao.FriendsDAO;
import com.server_for_spn.entity.Friends;
import com.server_for_spn.entity.Pet;
import com.server_for_spn.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Victor on 05.08.2018.
 */
@Service
public class FriendShipService {


    @Autowired
    private FriendsDAO friendsDAO;

    public void save(Friends friends){
        friendsDAO.save(friends);
    }

    public List<Friends> findAll(){
        return friendsDAO.findAll();
    }

    public Friends findOne(Long id){
        return friendsDAO.getOne(id);
    }

    public void delete(Long  id){
        friendsDAO.deleteById(id);
    }

    public void update(Friends friends){
        friendsDAO.save(friends);
    }


    /**
     * Returns true if friendship  between side1 and side2 exists
     * false if  not
     * @param side1
     * @param side2
     * @return
     */
    public boolean checkExistence(User side1, User side2){
        Friends f = friendsDAO.findBySide1AndSide2(side1, side2);
        if(f!=null)
            return true;
        f = friendsDAO.findBySide1AndSide2(side2, side1);
        return f != null;
    }



}
