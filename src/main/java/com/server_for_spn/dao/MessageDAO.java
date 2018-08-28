package com.server_for_spn.dao;


import com.server_for_spn.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageDAO extends JpaRepository<Message, Long> {
    @Query(value = "select * from message where friends_id =:id and user_to =:uId and is_read = false ",nativeQuery = true)
    List<Message> findMessagesByFriendsAndRead(@Param("id")Long id, @Param("uId") Long uId);

    @Query(value = "select count(*) from message where friends_id =:id and is_read = false ",nativeQuery = true)
    Integer countUnreaded(@Param("id")Long id);
}
