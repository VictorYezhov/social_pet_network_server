package com.server_for_spn.service;

import com.server_for_spn.entity.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CommentService  {

    void save(Comment comment);

    List<Comment> findAll();

    Comment findOne(Long id);

    void delete(Long  id);

    void update(Comment comment);

}
