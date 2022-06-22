package com.astroexpress.astrologer.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.databinding.ActivityHelpBinding;
import com.astroexpress.astrologer.utils.NetworkStats;

public class HelpActivity extends AppCompatActivity {

    ActivityHelpBinding binding;
    NetworkStats networkStats = new NetworkStats();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar));
        binding = ActivityHelpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.left_back);
        binding.toolbar.setNavigationOnClickListener(view -> onBackPressed());

        binding.contactUs.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),ContactUsActivity.class)));
        binding.raiseTicket.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),RaiseTicketActivity.class)));
        binding.raisedTicket.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),RaisedTicketActivity.class)));
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