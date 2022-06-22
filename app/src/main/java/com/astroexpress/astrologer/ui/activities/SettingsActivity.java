package com.astroexpress.astrologer.ui.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.databinding.ActivitySettingsBinding;
import com.astroexpress.astrologer.databinding.LogoutCustomDialogLayoutBinding;
import com.astroexpress.astrologer.utils.NetworkStats;
import com.astroexpress.astrologer.utils.SharedPreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    ActivitySettingsBinding binding;
    NetworkStats networkStats = new NetworkStats();
    Dialog dialog;
    LogoutCustomDialogLayoutBinding dialogLayoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar));
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.left_back);
        binding.toolbar.setNavigationOnClickListener(view -> onBackPressed());


        dialog = new Dialog(this);
        dialogLayoutBinding = LogoutCustomDialogLayoutBinding.inflate(getLayoutInflater());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogLayoutBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);


        binding.account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AccountActivity.class));
            }
        });

        binding.appInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AppInformationActivity.class));
            }
        });

        binding.help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),HelpActivity.class));
            }
        });

        binding.blockedAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),BlockedAccountsActivity.class));
            }
        });

        binding.termsAndCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),TermAndConditionActivity.class));
            }
        });

        binding.form16.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://www.incometaxindia.gov.in/forms/income-tax%20rules/103120000000007292.pdf"));
            startActivity(i);
        });

        binding.logout.setOnClickListener(view -> {
            dialog.show();
        });
        dialogLayoutBinding.logout.setOnClickListener(view -> {
            SharedPreferenceManager.logout(getApplicationContext());
            SharedPreferenceManager.removeStatus(getApplicationContext());
            Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            DashboardActivity.dashboardActivity.finish();
            finish();
        });
        dialogLayoutBinding.cancel.setOnClickListener(view -> dialog.dismiss());
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