package com.server_for_spn.fcm_notifications;

import com.server_for_spn.entity.User;

/**
 * Created by Victor on 05.08.2018.
 */
//TODO Implement firebase Push Notifications
public interface NotificationService {

    void sendNotification(User from, User to);

}
