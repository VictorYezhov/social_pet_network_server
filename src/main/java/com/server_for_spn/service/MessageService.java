package com.server_for_spn.service;

import com.server_for_spn.entity.Friends;
import com.server_for_spn.entity.Message;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface MessageService {

    void save(Message message);

    List<Message> findAll();

    Message findOne(Long id);

    void delete(Long  id);

    void update(Message message);

    List<Message> findAllUnreadMessages(Long id, Long uId);

    Integer countInreadedMessagesForUser(Long id);

}
