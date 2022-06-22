package com.astroexpress.astrologer.ui.activities;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.adapter.RecentInteractionAdapter;
import com.astroexpress.astrologer.databinding.ActivityRecentInteractionBinding;
import com.astroexpress.astrologer.models.response.UsersModel;
import com.astroexpress.astrologer.network.RetrofitClient;
import com.astroexpress.astrologer.utils.NetworkStats;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecentInteractionActivity extends AppCompatActivity {

    ActivityRecentInteractionBinding binding;
    RecentInteractionAdapter recentInteractionAdapter;
    NetworkStats networkStats = new NetworkStats();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar));
        binding = ActivityRecentInteractionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.left_back);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        getRecentList();

    }

    private void getRecentList() {
        RetrofitClient.getApiClient().recentUsers("1").enqueue(new Callback<UsersModel>() {
            @Override
            public void onResponse(@NonNull Call<UsersModel> call, @NonNull Response<UsersModel> response) {
                if (response.isSuccessful() && response.body() != null){
                    if (response.body().getCode().equals("200")){
                        recentInteractionAdapter = new RecentInteractionAdapter(getApplicationContext(),response.body().getResult());
                        binding.recentUsersView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
                        binding.recentUsersView.setAdapter(recentInteractionAdapter);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<UsersModel> call, @NonNull Throwable t) {

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