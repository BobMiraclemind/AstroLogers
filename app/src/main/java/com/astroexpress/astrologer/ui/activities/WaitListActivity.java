package com.astroexpress.astrologer.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.adapter.WaitlistAdapter;
import com.astroexpress.astrologer.databinding.ActivityWaitlistBinding;
import com.astroexpress.astrologer.utils.NetworkStats;

public class WaitListActivity extends AppCompatActivity {

    ActivityWaitlistBinding binding;
    NetworkStats networkStats = new NetworkStats();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar));
        binding = ActivityWaitlistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        WaitlistAdapter waitlistAdapter = new WaitlistAdapter(getApplicationContext());
        binding.waitlistView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.waitlistView.setAdapter(waitlistAdapter);
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