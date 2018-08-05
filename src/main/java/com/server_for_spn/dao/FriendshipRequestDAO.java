package com.server_for_spn.dao;

import com.server_for_spn.entity.FriendShipRequest;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Victor on 05.08.2018.
 */
public interface FriendshipRequestDAO extends JpaRepository<FriendShipRequest, Long> {
    FriendShipRequest findByAcceptorIdAndRequesterId(Long accepterId, Long requesterId);
}
