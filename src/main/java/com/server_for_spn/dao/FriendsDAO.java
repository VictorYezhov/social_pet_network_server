package com.server_for_spn.dao;

import com.server_for_spn.entity.Friends;
import com.server_for_spn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Victor on 05.08.2018.
 */
public interface FriendsDAO extends JpaRepository<Friends, Long> {

    Friends findBySide1AndSide2(User side1, User side2);


}
