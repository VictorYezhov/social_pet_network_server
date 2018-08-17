package com.server_for_spn.dao;

import com.server_for_spn.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageDAO extends JpaRepository<Message, Long> {
}
