package com.server_for_spn.dao;

import com.server_for_spn.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDAO extends JpaRepository<Comment, Long> {
}
