package com.server_for_spn.dao;

import com.server_for_spn.entity.UserState;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Victor on 14.08.2018.
 */
public interface UserStateDAO extends JpaRepository<UserState,Long> {
}
