package com.server_for_spn.fcm_notifications;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.*;
import com.server_for_spn.dao.UserDAO;
import com.server_for_spn.entity.City;
import com.server_for_spn.entity.Pet;
import com.server_for_spn.entity.User;
import com.server_for_spn.enums.NotificationType;
import com.server_for_spn.lockationServises.LocationService;
import com.server_for_spn.lockationServises.models.CoordinatesInfo;
import com.server_for_spn.lockationServises.models.UserAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Victor on 03.09.2018.
 */
@Service
public class SosNotifier {


    @Autowired
    private LocationService locationService;

    @Autowired
    private UserDAO userDAO;

    private AndroidConfig androidConfig;

    private ApnsConfig apnsConfig;

    private NotificationType notificationType = NotificationType.SOS;

    public SosNotifier(AndroidConfig androidConfig, ApnsConfig apnsConfig) {
        this.androidConfig = androidConfig;
        this.apnsConfig = apnsConfig;
    }

    public void sendNotification(Pet lostPet, City city) {

        ExecutorService service = Executors.newCachedThreadPool();

        List<User> users = userDAO.findAllByCity(city);


        if (users.isEmpty()){
            System.out.println("EMPTY");
        }


        for (User u:
              users) {
            Message message = Message.builder()
                    .setToken(u.getFcmToken())
                    .setApnsConfig(apnsConfig)
                    .setAndroidConfig(androidConfig)
                    .putData("type", notificationType.name())
                    .putData("petId", lostPet.getId().toString())
                    .setNotification(new Notification("Notification", "SOS")).build();
            FirebaseMessaging.getInstance().sendAsync(message).addListener(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Notification about sos to "+ u.getEmail());
                }
            }, service);
        }


//            try {
//                System.out.println(ap.get());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
        }
    }
