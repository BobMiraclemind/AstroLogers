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
import com.astroexpress.astrologer.ui.activities.ChatActivity;

public class MyNotification {

    public static void createNotification(Context context, int channelId, String userId, String astrologerId, String title, String description) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            String name = getString(R.string.channel_name);
//            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH; //Important for heads-up notification
            NotificationChannel channel = new NotificationChannel("1", "Notification", importance);
            channel.setDescription(description);
            channel.setShowBadge(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Intent intentAccept = new Intent(context, ChatActivity.class);
        intentAccept.putExtra("isFromNotification", true);
        intentAccept.putExtra("channelId", channelId);
        intentAccept.putExtra("userId", userId);
        intentAccept.putExtra("astrologerId", astrologerId);
        intentAccept.putExtra("username",title);

        PendingIntent pendingIntentAccept = PendingIntent.getActivity(context, 0, intentAccept, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);

        Intent intentReject = new Intent(context, ChatActivity.class);

        intentReject.putExtra("isFromNotification", true);
        intentReject.putExtra("isForCancelNotification", true);
        intentReject.putExtra("channelId", channelId);
        intentReject.putExtra("userId", userId);
        intentReject.putExtra("astrologerId", astrologerId);
        intentReject.putExtra("username",title);

        PendingIntent pendingIntentReject = PendingIntent.getActivity(context, 1, intentReject, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext(), "1");
        builder.setSmallIcon(R.drawable.astroexpress_logo);
        builder.setContentTitle(title);
        builder.setContentText(description);
        builder.setAutoCancel(true);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.addAction(R.drawable.astroexpress_logo, "Accept", pendingIntentAccept);
        builder.addAction(R.drawable.astroexpress_logo, "Reject", pendingIntentReject);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(channelId, builder.build());
    }

    public static void cancelNotification(Context context, int id) {

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.cancel(id);

    }
}