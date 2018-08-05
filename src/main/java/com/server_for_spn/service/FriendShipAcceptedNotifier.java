package com.server_for_spn.service;

import com.server_for_spn.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Victor on 05.08.2018.
 */
@Service
@Qualifier("friendshipAcceptedNotifier")
public class FriendShipAcceptedNotifier implements NotificationService{
    @Override
    public void sendNotification(User from, User to) {

    }
}
