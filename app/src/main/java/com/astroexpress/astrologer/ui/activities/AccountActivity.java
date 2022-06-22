package com.astroexpress.astrologer.ui.activities;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.databinding.ActivityAccountBinding;
import com.astroexpress.astrologer.utils.NetworkStats;
import com.astroexpress.astrologer.utils.StaticFields;

public class AccountActivity extends AppCompatActivity {

    ActivityAccountBinding binding;
    NetworkStats networkStats = new NetworkStats();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar));
        binding = ActivityAccountBinding.inflate(getLayoutInflater());
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

        binding.firstName.setText(StaticFields.astrologerData.getFirstname()==null?"":StaticFields.astrologerData.getFirstname());
        binding.lastName.setText(StaticFields.astrologerData.getLastname()==null?"":StaticFields.astrologerData.getLastname());
        binding.phoneNum.setText(StaticFields.astrologerData.getMobile()==null?"":StaticFields.astrologerData.getMobile());
        binding.aadharNum.setText(StaticFields.astrologerData.getAadhaarNumber()==null?"":StaticFields.astrologerData.getAadhaarNumber());
        binding.panNum.setText(StaticFields.astrologerData.getPanNumber()==null?"":StaticFields.astrologerData.getPanNumber());
        binding.email.setText(StaticFields.astrologerData.getEmail()==null?"":StaticFields.astrologerData.getEmail());
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