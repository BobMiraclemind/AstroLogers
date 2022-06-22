package com.astroexpress.astrologer.ui.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.databinding.ActivityChatBinding;
import com.astroexpress.astrologer.models.request.ChatRequestModel;
import com.astroexpress.astrologer.models.request.ChatSessionRequestModel;
import com.astroexpress.astrologer.models.response.ChatListResponseModel;
import com.astroexpress.astrologer.models.response.ChatSessionResponseModel;
import com.astroexpress.astrologer.models.response.NotifyUsersResponseModel;
import com.astroexpress.astrologer.models.response.SaveChatResponseModel;
import com.astroexpress.astrologer.models.response.UsersModel;
import com.astroexpress.astrologer.network.RetrofitClient;
import com.astroexpress.astrologer.utils.AllStaticMethods;
import com.astroexpress.astrologer.utils.AppConstants;
import com.astroexpress.astrologer.utils.MyNotification;
import com.astroexpress.astrologer.utils.MyNotificationManager;
import com.astroexpress.astrologer.utils.NetworkStats;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    ActivityChatBinding binding;

    final int SEEN_STATUS_PROCESS = -1;
    final int SEEN_STATUS_SENT = 0;
    final int SEEN_STATUS_DELIVERED = 1;
    final int SEEN_STATUS_READ = 2;
    final String TEXT_MESSAGE = "TextMessage";
    final String TYPING_STATUS = "TypingStatus";
    final String TERMINATE_STATUS = "TerminateStatus";

    String action;

    DatabaseReference dbRefPresenceSystem;
    DatabaseReference dbRefConversations;
    DatabaseReference dbRefStatus;
    DatabaseReference dbCallingRefBusyStatus;
    DatabaseReference dbCallingRefStatus;

    private boolean isFromNotification;
    private boolean isForCancelNotification;
    private int channelId;
    private String userId;
    private String astrologerId;
    private String username;
    private CountDownTimer countDownTimer;
    private ChildEventListener conversationListener;

    NetworkStats networkStats = new NetworkStats();
    private static final int REQUEST_CODE_SPEECH_INPUT = 101;
    private int seconds = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar));
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        action = getIntent().getAction();

        isFromNotification = getIntent().getBooleanExtra("isFromNotification", false);
        isForCancelNotification = getIntent().getBooleanExtra("isForCancelNotification", false);
        channelId = getIntent().getIntExtra("channelId", 0);
        userId = getIntent().getStringExtra("userId");
        astrologerId = getIntent().getStringExtra("astrologerId");
        username = getIntent().getStringExtra("username");

        binding.firstname.setText(username);

        MyNotification.cancelNotification(getApplicationContext(),channelId);

        runTimer();

        binding.back.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),DashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        dbRefPresenceSystem = FirebaseDatabase.getInstance().getReference("Chats").child("Astrologer").child(astrologerId);
        dbRefConversations = FirebaseDatabase.getInstance().getReference("Chats").child("User::Astrologer").child(userId + "::" + astrologerId).child("Conversations");
        dbRefStatus = FirebaseDatabase.getInstance().getReference("Chats").child("User::Astrologer").child(userId + "::" + astrologerId).child("Status");
        dbCallingRefBusyStatus = FirebaseDatabase.getInstance().getReference("Chats").child("Astrologer");
        dbCallingRefStatus = FirebaseDatabase.getInstance().getReference("Chats").child("Astrologer").child(astrologerId).child("Status");

        if (isFromNotification && isForCancelNotification) {

            dbCallingRefStatus.child("RequestToAstrologer").setValue("Rejected").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });

//            MyNotificationManager.cancelNotification(getApplicationContext(), channelId);
            MyNotification.cancelNotification(getApplicationContext(),channelId);
            finish();

            return;
        } else {

            if (getIntent().getStringExtra("sessionId") == null) {

                ChatSessionRequestModel chatSessionRequestModel = new ChatSessionRequestModel(userId, astrologerId);

                RetrofitClient.getApiClient().createChatSession(chatSessionRequestModel).enqueue(new Callback<ChatSessionResponseModel>() {
                    @Override
                    public void onResponse(Call<ChatSessionResponseModel> call, Response<ChatSessionResponseModel> response) {
                        try {
                            if (response.body() != null && response.isSuccessful()) {
                                if (response.body().getCode().equals("200")) {


                                    dbCallingRefBusyStatus.child(astrologerId).child("BusyStatus").setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            dbCallingRefStatus.child("RequestToAstrologer").setValue("Accepted").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                }
            });

            MyNotificationManager.cancelNotification(getApplicationContext(), channelId);

                                            dbRefStatus.child(TERMINATE_STATUS).addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if (snapshot.getValue() != null && (Boolean) snapshot.getValue()) {
                                                        dbCallingRefBusyStatus.child(astrologerId).child("BusyStatus").setValue(false).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {

                                                            }
                                                        });

                                                        dbRefStatus.child(TERMINATE_STATUS).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {

                                                            }
                                                        });
//                                                    Toast.makeText(ChatActivity.this, "User left this chat session", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }
                                    });

                                }
                            }
                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void onFailure(Call<ChatSessionResponseModel> call, Throwable t) {

                    }
                });

            } else {
                dbRefStatus.child(TERMINATE_STATUS).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue() != null && (Boolean) snapshot.getValue()) {
                            dbCallingRefBusyStatus.child(astrologerId).child("BusyStatus").setValue(false).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });

                            dbRefStatus.child(TERMINATE_STATUS).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });
//                                                    Toast.makeText(ChatActivity.this, "User left this chat session", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        }


        dbRefConversations.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.getValue() != null) {
                    String res = snapshot.getValue().toString();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        conversationListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                binding.llTypingStatus.setVisibility(View.GONE);

                if (snapshot.getValue() == null)
                    return;

                ChatRequestModel chatRequestModelResult = snapshot.getValue(ChatRequestModel.class);
                if (chatRequestModelResult.getIsSentByUser()) {
                    chatRequestModelResult.setSeenStatus("" + SEEN_STATUS_READ);
                    setReceivedMessageUI(chatRequestModelResult);
                    dbRefConversations.child(chatRequestModelResult.getFirebaseChatId()).setValue(chatRequestModelResult).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                            }
                        }
                    });

                }
//               else{
//                   setSendMessageUI(Integer.parseInt(chatRequestModel.getSeenStatus()),chatRequestModel);
//               }

                scrollToBottom();
//                Toast.makeText(context, "Added " + snapshot.getKey(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


                if (snapshot.getValue() == null)
                    return;

                ChatRequestModel chatRequestModelResult = snapshot.getValue(ChatRequestModel.class);
                if (!chatRequestModelResult.getIsSentByUser()) {

                    List<ChatRequestModel> chatRequestModelList = new ArrayList<>();

                    ChatRequestModel chatRequestModel = new ChatRequestModel(
                            userId,
                            astrologerId,
                            null,
                            null,
                            "" + SEEN_STATUS_READ,
                            null,
                            chatRequestModelResult.getFirebaseChatId()
                    );

                    chatRequestModelList.add(chatRequestModel);

                    callUpdateChatApi(chatRequestModelList);
                    dbRefConversations.child(chatRequestModelResult.getFirebaseChatId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });

                }
                //  Toast.makeText(context, "" + snapshot.getKey(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        dbCallingRefStatus.child("RequestToAstrologer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null && snapshot.getValue().toString().equals("Calling")) {
                    dbCallingRefStatus.child("RequestToAstrologer").setValue("Connected");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dbRefStatus.child(TYPING_STATUS).child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Toast.makeText(context, "hh", Toast.LENGTH_SHORT).show();
                if (snapshot.getValue() == null)
                    return;

                if ((Boolean) snapshot.getValue()) {
                    binding.llTypingStatus.setVisibility(View.VISIBLE);
                    scrollToBottom();
                } else {
                    binding.llTypingStatus.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.send.setOnClickListener(view -> {

            if (!binding.message.getText().toString().trim().equals("")) {

                String chatId = dbRefConversations.push().getKey();

                ChatRequestModel chatRequestModel = new ChatRequestModel(
                        userId,
                        astrologerId,
                        binding.message.getText().toString(),
                        false,
                        "0",
                        TEXT_MESSAGE,
                        chatId
                );

                View textMessageView = setSendMessageUI(SEEN_STATUS_PROCESS, chatRequestModel);

                dbRefConversations.child(chatId).setValue(chatRequestModel)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    scrollToBottom();
                                    Toast.makeText(getApplicationContext(), "message sent", Toast.LENGTH_SHORT).show();
                                    callSaveChatApi(chatRequestModel, textMessageView);
                                    //handle seen status UI
                                    //call to save chat from api
                                }
                            }
                        });

                binding.message.setText("");

            }

        });

        scrollToBottom();

        binding.message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!binding.message.getText().toString().trim().equals(""))
                    updateTypingStatus();
            }
        });

        callGetChatApi();

//        getDataForRecentUser();

    }

    @Override
    protected void onPause() {
        super.onPause();
        dbRefConversations.removeEventListener(conversationListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbRefConversations.addChildEventListener(conversationListener);
    }
    private void getDataRequestUser(String userID){
        RetrofitClient.getApiClient().getUserForRequest(userID).enqueue(new Callback<NotifyUsersResponseModel>() {
            @Override
            public void onResponse(Call<NotifyUsersResponseModel> call, Response<NotifyUsersResponseModel> response) {
                try {
                    if (response.body()!=null && response.isSuccessful()){
                        if (response.body().getCode().equals("200")){
                            NotifyUsersResponseModel.Result userData = response.body().getResult();
                            binding.firstname.setText(userData.getFirstname());
                            binding.lastname.setText(userData.getLastname());
                        }
                    }
                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<NotifyUsersResponseModel> call, Throwable t) {

            }
        });
    }

    private void getDataForRecentUser()     {
        RetrofitClient.getApiClient().recentUsers(astrologerId).enqueue(new Callback<UsersModel>() {
            @Override
            public void onResponse(Call<UsersModel> call, Response<UsersModel> response) {
                if (response.isSuccessful() && response.body() != null){
                    if (response.body().getCode().equals("200")){
                        List<UsersModel.Result> users = response.body().getResult();
                        for(int i = 0;i<=users.size();i++){
                            if (users.get(i).getUserId() == userId){
                                Glide.with(getApplicationContext())
                                        .load(users.get(i).getProfileImageUrl())
                                        .centerCrop()
                                        .placeholder(R.drawable.place_holder)
                                        .into(binding.image);
                                binding.firstname.setText(users.get(i).getFirstname()==null?"":users.get(i).getFirstname());
                                binding.lastname.setText(users.get(i).getLastName()==null?"":users.get(i).getLastName());
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UsersModel> call, Throwable t) {

            }
        });
    }

    private void callUpdateChatApi(List<ChatRequestModel> chatRequestModelList) {


        RetrofitClient.getApiClient().updateChat(chatRequestModelList).enqueue(new Callback<SaveChatResponseModel>() {
            @Override
            public void onResponse(Call<SaveChatResponseModel> call, Response<SaveChatResponseModel> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {

                        } else {
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    AllStaticMethods.saveException(e);
                }

            }

            @Override
            public void onFailure(Call<SaveChatResponseModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void callSaveChatApi(ChatRequestModel chatRequestModel, View textMessageView) {

        chatRequestModel.setSeenStatus("" + SEEN_STATUS_SENT);

        RetrofitClient.getApiClient().saveChat(chatRequestModel).enqueue(new Callback<SaveChatResponseModel>() {
            @Override
            public void onResponse(Call<SaveChatResponseModel> call, Response<SaveChatResponseModel> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {

                            ImageView imgSentStatus = textMessageView.findViewById(R.id.imgSentStatus);
                            imgSentStatus.setImageResource(R.drawable.ic_done);

                            dbRefConversations.child(chatRequestModel.getFirebaseChatId()).child("SeenStatus").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    if (snapshot.getValue() != null) {
                                        ImageView imgSentStatus = textMessageView.findViewById(R.id.imgSentStatus);

                                        if (snapshot.getValue().toString().equals("" + SEEN_STATUS_READ)) {
                                            imgSentStatus.setImageResource(R.drawable.ic_seen);
                                        } else if (snapshot.getValue().toString().equals("" + SEEN_STATUS_DELIVERED)) {
                                            imgSentStatus.setImageResource(R.drawable.ic_delivered);
                                        }

//                                        List<ChatRequestModel> chatRequestModelList = new ArrayList<>();
//
//                                        ChatRequestModel chatRequestModel1 = new ChatRequestModel(
//                                                userId,
//                                                astrologerId,
//                                                null,
//                                                null,
//                                                snapshot.getValue().toString(),
//                                                null,
//                                                chatRequestModel.getFirebaseChatId()
//                                        );
//
//                                        chatRequestModelList.add(chatRequestModel1);
//
//                                        callUpdateChatApi(chatRequestModelList);

                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        } else {
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    AllStaticMethods.saveException(e);
                }

            }

            @Override
            public void onFailure(Call<SaveChatResponseModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void callGetChatApi() {
        RetrofitClient.getApiClient().getChatList(userId, astrologerId).enqueue(new Callback<ChatListResponseModel>() {
            @Override
            public void onResponse(Call<ChatListResponseModel> call, Response<ChatListResponseModel> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {

                            List<ChatRequestModel> chatRequestModelList = new ArrayList<>();
                            for (ChatRequestModel result : response.body().getResult()) {
                                if (!result.getIsSentByUser()) {
                                    setSendMessageUI(Integer.parseInt(result.getSeenStatus()), result);
                                } else {
                                    setReceivedMessageUI(result);

//                                    ChatRequestModel chatRequestModel = new ChatRequestModel(
//                                            userId,
//                                            astrologerId,
//                                            null,
//                                            null,
//                                            "" + SEEN_STATUS_READ,
//                                            null,
//                                            result.getFirebaseChatId()
//                                    );
//
//                                    chatRequestModelList.add(chatRequestModel);
                                }
                            }

//                            if (chatRequestModelList.size() > 0)
//                                callUpdateChatApi(chatRequestModelList);

                        } else {
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    AllStaticMethods.saveException(e);
                }
            }

            @Override
            public void onFailure(Call<ChatListResponseModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void updateTypingStatus() {

        if (countDownTimer != null)
            countDownTimer.cancel();

//        TypingStatusModel typingStatusModel = new TypingStatusModel(true, true);
        dbRefStatus.child(TYPING_STATUS).child("astrologer").setValue(true);

        countDownTimer = new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                countDownTimer = null;
                dbRefStatus.child(TYPING_STATUS).child("astrologer").setValue(false);
            }

        };

        countDownTimer.start();
    }

    private void scrollToBottom() {
        binding.scrollView.post(() -> binding.scrollView.fullScroll(View.FOCUS_DOWN));
    }

    private View setReceivedMessageUI(ChatRequestModel chatRequestModel) {

        View view = null;

        switch (chatRequestModel.getChatType()) {
            case TEXT_MESSAGE:
                view = loadTextChatReceived(Integer.parseInt(chatRequestModel.getSeenStatus()), chatRequestModel);
                break;
        }

        return view;
    }

    private View setSendMessageUI(int seen_status, ChatRequestModel chatRequestModel) {

        View view = null;

        switch (chatRequestModel.getChatType()) {
            case TEXT_MESSAGE:
                view = loadTextChatSent(seen_status, chatRequestModel);
                break;
        }

        return view;
    }

    private View loadTextChatReceived(int seen_status, ChatRequestModel chatRequestModel) {

        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_list_receiver, null);
        TextView txtMessage = view.findViewById(R.id.txtMessage);
        TextView txtTime = view.findViewById(R.id.txtTime);

        txtMessage.setText(chatRequestModel.getChatMessage());
        txtTime.setText(new SimpleDateFormat("hh:mm a").format(new Date()));

        //handle seen status UI
        switch (seen_status) {

        }

        binding.llList.addView(view);

        return view;
    }

    private View loadTextChatSent(int seen_status, ChatRequestModel chatRequestModel) {

        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_list_sender, null);
        TextView txtMessage = view.findViewById(R.id.txtMessage);
        TextView txtTime = view.findViewById(R.id.txtTime);

        txtMessage.setText(chatRequestModel.getChatMessage());
        txtTime.setText(new SimpleDateFormat("hh:mm a").format(new Date()));

        ImageView imgSentStatus = view.findViewById(R.id.imgSentStatus);


        //handle seen status UI
        switch (seen_status) {
            case SEEN_STATUS_READ:
                imgSentStatus.setImageResource(R.drawable.ic_seen);
                break;
            case SEEN_STATUS_DELIVERED:
                imgSentStatus.setImageResource(R.drawable.ic_delivered);
                break;
            case SEEN_STATUS_SENT:
                imgSentStatus.setImageResource(R.drawable.ic_done);
                break;
        }

        binding.llList.addView(view);

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void runTimer() {

        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override

            public void run()
            {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format(Locale.getDefault(), "%02d:%02d", minutes, secs);
                binding.createdDateTime.setText(time+" mins");
                seconds++;
                handler.postDelayed(this, 1000);
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkStats, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkStats);
        super.onStop();
    }
}