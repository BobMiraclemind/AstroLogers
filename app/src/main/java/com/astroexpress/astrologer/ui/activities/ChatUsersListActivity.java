package com.astroexpress.astrologer.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.adapter.ChatListAdapter;
import com.astroexpress.astrologer.databinding.ActivityChatUsersListBinding;
import com.astroexpress.astrologer.models.response.UsersModel;
import com.astroexpress.astrologer.network.RetrofitClient;
import com.astroexpress.astrologer.utils.AllStaticMethods;
import com.astroexpress.astrologer.utils.NetworkStats;
import com.astroexpress.astrologer.utils.StaticFields;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatUsersListActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    ActivityChatUsersListBinding binding;
    ChatListAdapter chatListAdapter;
    NetworkStats networkStats = new NetworkStats();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar));
        binding = ActivityChatUsersListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.left_back);
        binding.toolbar.setNavigationOnClickListener(view -> onBackPressed());
        binding.progress.setVisibility(View.VISIBLE);

        getChatListData();

    }

    private void getChatListData() {
        RetrofitClient.getApiClient().recentUsers(StaticFields.astrologerData.getAstrologerId()).enqueue(new Callback<UsersModel>() {
            @Override
            public void onResponse(Call<UsersModel> call, Response<UsersModel> response) {
                try {
                    if (response.isSuccessful() && response.body() != null){
                        if (response.body().getCode().equals("200")){
                            binding.statusMsg.setVisibility(View.GONE);
                            chatListAdapter = new ChatListAdapter(getApplicationContext(),response.body().getResult());
                            binding.recentChatUsersView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
                            binding.recentChatUsersView.setAdapter(chatListAdapter);
                            binding.progress.setVisibility(View.GONE);
                        }else {
                            binding.progress.setVisibility(View.GONE);
                            binding.statusMsg.setVisibility(View.VISIBLE);
                            binding.statusMsg.setText(response.body().getMessage());
                        }
                    }else {
                        binding.progress.setVisibility(View.GONE);
                        binding.statusMsg.setVisibility(View.VISIBLE);
                        binding.statusMsg.setText(response.body().getMessage());
                    }
                } catch (Exception e){
                    AllStaticMethods.saveException(e);
                }
            }

            @Override
            public void onFailure(Call<UsersModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
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