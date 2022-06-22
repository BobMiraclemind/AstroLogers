package com.astroexpress.astrologer.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.network.RetrofitClient;
import com.astroexpress.astrologer.ui.activities.ChatActivity;

public class MyNotificationManager {

    public static void sendNewChatRequestNotification(Context context, int channelId, String userId, String astrologerId, String title, String description) {

        Intent intentAction = new Intent(context, ChatActivity.class)
                .putExtra("UserId", userId)
                .putExtra("AstrologerId", astrologerId);

        //This is optional if you have more than one buttons and want to differentiate between two
        intentAction.putExtra("action", AppConstants.CHAT_TO_ASTROLOGER);

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel =new NotificationChannel("My notification","My notification",NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager =context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intentAccept = new Intent(context, ChatActivity.class);
        intentAccept.putExtra("isFromNotification", true);
        intentAccept.putExtra("channelId", channelId);
        intentAccept.putExtra("userId", userId);
        intentAccept.putExtra("astrologerId", astrologerId);

        PendingIntent pendingIntentAccept = PendingIntent.getActivity(context, 0, intentAccept, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentReject = new Intent(context, ChatActivity.class);

        intentReject.putExtra("isFromNotification", true);
        intentReject.putExtra("isForCancelNotification", true);
        intentReject.putExtra("channelId", channelId);
        intentReject.putExtra("userId", userId);
        intentReject.putExtra("astrologerId", astrologerId);

        PendingIntent pendingIntentReject = PendingIntent.getActivity(context, 1, intentReject, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder b = new NotificationCompat.Builder(context,"My notification");

        b.setContentTitle(title);
        b.setContentText(description);
        b.setSmallIcon(R.drawable.astroexpress_logo);
        b.setAutoCancel(true);
        b.setSound(alarmSound);
        b.setPriority(Notification.PRIORITY_MAX);
        b.addAction(R.drawable.astroexpress_logo, "Accept",
                pendingIntentAccept);
        b.addAction(R.drawable.astroexpress_logo, "Reject",
                pendingIntentReject);


        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(channelId, b.build());


    }

    public static void cancelNotification(Context context,int id){

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.cancel(id);

    }

}
