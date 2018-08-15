package com.server_for_spn.fcm_notifications;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * Created by Victor on 15.08.2018.
 */
@Service
public class AndroidNotificationConfigFactory {


    @Bean
    @Scope(value = "prototype")
    public static AndroidConfig getAndroidConfiguration(){
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey("personal")
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder().setTag("personal").build())
                .build();
    }

}
