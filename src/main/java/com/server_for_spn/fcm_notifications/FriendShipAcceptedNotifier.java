package com.server_for_spn.fcm_notifications;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.*;
import com.server_for_spn.entity.User;
import com.server_for_spn.enums.NotificationType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

/**
 * Created by Victor on 05.08.2018.
 */
@Service
@Qualifier("friendshipAcceptedNotifier")
public class FriendShipAcceptedNotifier implements NotificationService{

    private AndroidConfig androidConfig;

    private ApnsConfig apnsConfig;

    private NotificationType notificationType = NotificationType.FRIENDSHIPACCEPTED;

    public FriendShipAcceptedNotifier(AndroidConfig androidConfig, ApnsConfig apnsConfig) {
        this.androidConfig = androidConfig;
        this.apnsConfig = apnsConfig;
    }

    @Override
    public void sendNotification(User from, User to) {

        Message message = Message.builder()
                .setToken(to.getFcmToken())
                .setApnsConfig(apnsConfig)
                .setAndroidConfig(androidConfig)
                .putData("type", notificationType.name())
                .putData("new_friend_id", from.getId().toString())
                .setNotification(new Notification("Notification", from.getId().toString()))
                .build();

        try {
            String response = FirebaseMessaging.getInstance().sendAsync(message).get();
            System.out.println("Sent message: " + response);
        }catch (ExecutionException e){
            e.printStackTrace();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
