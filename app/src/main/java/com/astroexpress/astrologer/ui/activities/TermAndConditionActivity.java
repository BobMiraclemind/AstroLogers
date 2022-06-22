package com.astroexpress.astrologer.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.databinding.ActivityTermAndConditionBinding;
import com.astroexpress.astrologer.utils.NetworkStats;

public class TermAndConditionActivity extends AppCompatActivity {

    ActivityTermAndConditionBinding binding;
    NetworkStats networkStats = new NetworkStats();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar));
        binding = ActivityTermAndConditionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.myWebView.loadUrl("https://astroexpress.in/terms.html");

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