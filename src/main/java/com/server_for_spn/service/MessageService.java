package com.server_for_spn.service;

import com.server_for_spn.entity.Message;

import java.util.List;

public interface MessageService {

    void save(Message message);

    List<Message> findAll();

    Message findOne(Long id);

    void delete(Long  id);

    void update(Message message);

}
