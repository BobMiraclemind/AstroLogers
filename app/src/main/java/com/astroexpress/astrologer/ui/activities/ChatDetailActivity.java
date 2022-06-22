package com.astroexpress.astrologer.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.databinding.ActivityCallDetailBinding;
import com.astroexpress.astrologer.databinding.ActivityChatDetailBinding;
import com.astroexpress.astrologer.models.request.ChatRequestModel;
import com.astroexpress.astrologer.models.response.ChatListResponseModel;
import com.astroexpress.astrologer.network.RetrofitClient;
import com.astroexpress.astrologer.utils.AllStaticMethods;
import com.astroexpress.astrologer.utils.AppConstants;
import com.astroexpress.astrologer.utils.SharedPreferenceManager;
import com.astroexpress.astrologer.utils.StaticFields;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatDetailActivity extends AppCompatActivity {

    ActivityChatDetailBinding binding;
    String userId,firstname,lastname,imageUrl;
    final String TEXT_MESSAGE = "TextMessage";
    final int SEEN_STATUS_PROCESS = -1;
    final int SEEN_STATUS_SENT = 0;
    final int SEEN_STATUS_DELIVERED = 1;
    final int SEEN_STATUS_READ = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar));
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.left_back);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        userId = getIntent().getStringExtra("uid");
        firstname = getIntent().getStringExtra("firstname");
        lastname = getIntent().getStringExtra("lastname");
        imageUrl = getIntent().getStringExtra("profile_img");

        binding.firstname.setText(firstname);
        binding.lastname.setText(lastname);
        Glide.with(getApplicationContext())
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.place_holder)
                .into(binding.image);

        callGetChatApi();
    }

    private void callGetChatApi() {
        RetrofitClient.getApiClient().getChatList(userId, SharedPreferenceManager.getUserData(getApplicationContext()).getAstrologerId()).enqueue(new Callback<ChatListResponseModel>() {
            @Override
            public void onResponse(Call<ChatListResponseModel> call, Response<ChatListResponseModel> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {
                            binding.progress.setVisibility(View.GONE);
                            binding.statusMsg.setVisibility(View.GONE);
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
                            binding.progress.setVisibility(View.GONE);
                            binding.statusMsg.setVisibility(View.VISIBLE);
                            binding.statusMsg.setText(response.body().getMessage());
                        }

                    } else {
                        binding.progress.setVisibility(View.GONE);
                        binding.statusMsg.setVisibility(View.VISIBLE);
                        binding.statusMsg.setText(response.body().getMessage());
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
}