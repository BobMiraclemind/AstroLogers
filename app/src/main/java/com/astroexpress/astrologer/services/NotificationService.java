package com.astroexpress.astrologer.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.astroexpress.astrologer.models.response.NotifyUsersResponseModel;
import com.astroexpress.astrologer.network.RetrofitClient;
import com.astroexpress.astrologer.utils.MyNotification;
import com.astroexpress.astrologer.utils.MyNotificationManager;
import com.astroexpress.astrologer.utils.SharedPreferenceManager;
import com.astroexpress.astrologer.utils.StaticFields;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationService extends Service {

    DatabaseReference dbRefStatus;
    private static final String TAG = "Bobb";
    String userId,firstname,lastname,userImgUrl,fullname;
    NotifyUsersResponseModel.Result userData;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: ");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        dbRefStatus = FirebaseDatabase.getInstance().getReference("Chats")
                .child("Astrologer")
                .child(SharedPreferenceManager.getUserData(getApplicationContext()).getAstrologerId())
                .child("Status");

        dbRefStatus.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("RequestToAstrologer").getValue() != null && snapshot.child("RequestToAstrologer").getValue().toString().equals("Calling")) {
                    if (snapshot.child("UserId").getValue() != null) {
//                        userId = snapshot.child("UserId").getValue().toString();
//                        MyNotificationManager.sendNewChatRequestNotification(getApplicationContext(),Integer.parseInt(snapshot.child("UserId").getValue().toString()),  snapshot.child("UserId").getValue().toString(), StaticFields.astrologerData.getAstrologerId(), " User", "Wants to chat with you");
                        RetrofitClient.getApiClient().getUserForRequest(snapshot.child("UserId").getValue().toString()).enqueue(new Callback<NotifyUsersResponseModel>() {
                            @Override
                            public void onResponse(Call<NotifyUsersResponseModel> call, Response<NotifyUsersResponseModel> response) {
                                userData = response.body().getResult();

                                MyNotification.createNotification(getApplicationContext(),
                                        Integer.parseInt(snapshot.child("UserId").getValue().toString()),
                                        snapshot.child("UserId").getValue().toString(),
                                        SharedPreferenceManager.getUserData(getApplicationContext()).getAstrologerId(),
                                        userData.getFirstname() + " " + userData.getLastname(), "user wants to chat with you");

                            }

                            @Override
                            public void onFailure(Call<NotifyUsersResponseModel> call, Throwable t) {

                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.i(TAG, "onStartCommand: ");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i(TAG, "onStart: ");
    }
}
