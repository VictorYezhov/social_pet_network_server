package com.server_for_spn.service;

import com.server_for_spn.dao.FriendsDAO;
import com.server_for_spn.dao.FriendshipRequestDAO;
import com.server_for_spn.entity.FriendShipRequest;
import com.server_for_spn.entity.Friends;
import com.server_for_spn.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Victor on 05.08.2018.
 */
@Service
public class FriendShipRequestService {

    @Autowired
    private FriendshipRequestDAO requestDAO;

    public void save(FriendShipRequest friends){
        requestDAO.save(friends);
    }

    public List<FriendShipRequest> findAll(){
        return requestDAO.findAll();
    }

    public FriendShipRequest findOne(Long id){
        return requestDAO.getOne(id);
    }

    public void delete(Long  id){
        requestDAO.deleteById(id);
    }

    public void update(FriendShipRequest request){
        requestDAO.save(request);
    }

    public List<FriendShipRequest> findAllForAcceptor(Long acceptorId){
        return requestDAO.findAllByAcceptorIdAndPersistedOnClientSide(acceptorId, false);
    }

    public FriendShipRequest findByAcceptorAndRequester(Long acceptor, Long requester){


        return requestDAO.findByAcceptorIdAndRequesterId(acceptor,requester);
    }

    public boolean checkExistence(User side1, User side2){
        FriendShipRequest f = requestDAO.findByAcceptorIdAndRequesterId(side1.getId(), side2.getId());
        if(f!=null)
            return true;
        f = requestDAO.findByAcceptorIdAndRequesterId(side2.getId(), side1.getId());
        return f != null;
    }



}
