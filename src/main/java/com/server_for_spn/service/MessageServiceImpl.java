package com.server_for_spn.service;

import com.server_for_spn.dao.MessageDAO;
import com.server_for_spn.entity.Friends;
import com.server_for_spn.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    MessageDAO messageDAO;

    @Override
    public void save(Message message) {
        messageDAO.save(message);
    }

    @Override
    public List<Message> findAll() {
        return messageDAO.findAll();
    }

    @Override
    public Message findOne(Long id) {
        return messageDAO.getOne(id);
    }

    @Override
    public void delete(Long id) {
        messageDAO.deleteById(id);
    }

    @Override
    public void update(Message message) {
        messageDAO.save(message);
    }

    @Override
    public Integer countInreadedMessagesForUser(Long id) {
        return messageDAO.countUnreaded(id);
    }

    @Override
    public List<Message> findAllUnreadMessages(Long id) {
        return messageDAO.findMessagesByFriendsAndRead(id);
    }
}
