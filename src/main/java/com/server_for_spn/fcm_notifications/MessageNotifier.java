package com.server_for_spn.fcm_notifications;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.*;
import com.server_for_spn.entity.User;
import com.server_for_spn.enums.NotificationType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@Qualifier("messageNotifier")
public class MessageNotifier implements NotificationService{

    private AndroidConfig androidConfig;

    private ApnsConfig apnsConfig;

    private NotificationType notificationType = NotificationType.MESSAGESENT;

    public MessageNotifier(AndroidConfig androidConfig, ApnsConfig apnsConfig){
        this.androidConfig = androidConfig;
        this.apnsConfig = apnsConfig;
    }

    @Override
    public void sendNotification(User from, User to) {

        //System.out.println("message text ----> " + from.additionalField.toString());
        Message message = Message.builder()
                .setToken(to.getFcmToken())
                .setApnsConfig(apnsConfig)
                .setAndroidConfig(androidConfig)
                .putData("type", notificationType.name())
                .putData("message_text", from.additionalField.toString())
                .putData("name_from", from.getName())
                .putData("user_from", from.getId().toString())
                .putData("user_to", to.getId().toString())
                .putData("friends_id",to.additionalField.toString())
                .setNotification(new Notification("Notification", from.getId().toString())).build();




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
