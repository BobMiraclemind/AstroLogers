package com.astroexpress.astrologer.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.adapter.BlockedAccountAdapter;
import com.astroexpress.astrologer.databinding.ActivityBlockedAccountsBinding;

public class BlockedAccountsActivity extends AppCompatActivity {

    ActivityBlockedAccountsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBlockedAccountsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitle("Blocked Accounts");
        binding.toolbar.setTitleTextColor(getResources().getColor(R.color.text_color));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.left_back);
        binding.toolbar.setNavigationOnClickListener(view -> onBackPressed());

        BlockedAccountAdapter blockedAccountAdapter = new BlockedAccountAdapter(getApplicationContext());
        binding.blockedAccountList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.blockedAccountList.setAdapter(blockedAccountAdapter);
    }
}