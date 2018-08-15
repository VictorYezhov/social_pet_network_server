package com.server_for_spn.fcm_notifications;

import com.google.firebase.messaging.*;
import com.server_for_spn.entity.Pet;
import com.server_for_spn.entity.User;
import com.server_for_spn.enums.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

/**
 * Created by Victor on 05.08.2018.
 */
@Service
@Qualifier("friendshipRequestNotifier")
public class FriendshipRequestNotifier implements NotificationService {


    private AndroidConfig androidConfig;

    private ApnsConfig apnsConfig;


    private NotificationType notificationType = NotificationType.FRIEDSHIPREQUEST;

    public FriendshipRequestNotifier(AndroidConfig androidConfig, ApnsConfig apnsConfig) {
        this.androidConfig = androidConfig;
        this.apnsConfig = apnsConfig;
    }

    @Override
    public void sendNotification(User from, User to) {


        Pet userPet = null;
        for (Pet p:
             from.getPetList()) {
            if(p.getId().equals(from.getUserState().getCurrentPetChoose())){
                userPet = p;
            }
        }
        String petName;
        String petBreed;
        if(userPet == null){
            petName ="Not Set";
            petBreed = "Not Set";
        }else {
            petName = userPet.getName();
            petBreed = userPet.getBreed().getName();
        }



        Message message = Message.builder().setToken(to.getFcmToken())
                .putData("type", notificationType.name())
                .putData("id", from.getId().toString())
                .putData("requestId", to.additionalField.toString())
                .putData("name", from.getName())
                .putData("surname",from.getFamilyName())
                .putData("city", from.getCity().getName())
                .putData("country", from.getCity().getCountry().getName())
                .putData("petName", petName)
                .putData("petBreed", petBreed)
                .setApnsConfig(apnsConfig).setAndroidConfig(androidConfig)
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
