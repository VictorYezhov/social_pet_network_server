package com.server_for_spn.service;

import com.server_for_spn.dao.CommentDAO;
import com.server_for_spn.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentDAO commentDAO;

    @Override
    public void save(Comment comment) {
        commentDAO.save(comment);
    }

    @Override
    public List<Comment> findAll() {
        return commentDAO.findAll();
    }

    @Override
    public Comment findOne(Long id) {
        return commentDAO.getOne(id);
    }

    @Override
    public void delete(Long id) {
        commentDAO.deleteById(id);
    }

    @Override
    public void update(Comment comment) {
        commentDAO.save(comment);
    }
}
