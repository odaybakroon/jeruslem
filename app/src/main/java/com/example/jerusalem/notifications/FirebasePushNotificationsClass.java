package com.example.jerusalem.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.jerusalem.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FirebasePushNotificationsClass extends FirebaseMessagingService {
   String name =  FirebasePushNotificationsClass.class.getSimpleName();
    public FirebasePushNotificationsClass() {
        super();
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        getFirebaseMessage(remoteMessage.getNotification().getTitle() , remoteMessage.getNotification().getBody());

         Map<String, String> data = remoteMessage.getData();
         String line1 = data.get("line1");
         String line2 = data.get("line2");



    }
    private void getFirebaseMessage(String title , String msg){
      NotificationCompat.Builder builder = new NotificationCompat.Builder(this , "ch1")
              .setSmallIcon(R.drawable.ic_launcher_background)
              .setContentTitle(title)
              .setContentText(msg)
              .setAutoCancel(true);

        NotificationManagerCompat managerCompat  = NotificationManagerCompat.from(this);
        managerCompat.notify(101, builder.build());


    }
}
