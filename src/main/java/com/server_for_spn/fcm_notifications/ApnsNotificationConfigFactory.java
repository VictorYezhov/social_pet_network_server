package com.server_for_spn.fcm_notifications;

import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created by Victor on 15.08.2018.
 */
@Service
public class ApnsNotificationConfigFactory {


    @Bean
    @Scope(value = "prototype")
    public static ApnsConfig getApnsConfig(){
       return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory("personal").setThreadId("personal").build())
                .build();
    }

}
