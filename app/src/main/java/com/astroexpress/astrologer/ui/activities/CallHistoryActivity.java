package com.astroexpress.astrologer.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.adapter.CallHistoryAdapter;
import com.astroexpress.astrologer.databinding.ActivityCallHistoryBinding;
import com.astroexpress.astrologer.models.CallHistoryModel;
import com.astroexpress.astrologer.utils.NetworkStats;

import java.util.ArrayList;

public class CallHistoryActivity extends AppCompatActivity {

    ActivityCallHistoryBinding binding;
    private ArrayList<CallHistoryModel> callHistoryModelArrayList;
    private CallHistoryAdapter adapter;

    NetworkStats networkStats = new NetworkStats();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar));
        binding = ActivityCallHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.left_back);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        callHistoryModelArrayList = new ArrayList<>();
        getCallHistoryList();
        adapter = new CallHistoryAdapter(getApplicationContext(),callHistoryModelArrayList);
        binding.callHistoryView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.callHistoryView.setAdapter(adapter);
    }

    private void getCallHistoryList() {
        ////add the data here to array list
        callHistoryModelArrayList.add(new CallHistoryModel("Bob","9:04 mins","9:05 PM"));
        callHistoryModelArrayList.add(new CallHistoryModel("Bob","9:04 mins","9:05 PM"));
        callHistoryModelArrayList.add(new CallHistoryModel("Bob","9:04 mins","9:05 PM"));
        callHistoryModelArrayList.add(new CallHistoryModel("Bob","9:04 mins","9:05 PM"));
        callHistoryModelArrayList.add(new CallHistoryModel("Bob","9:04 mins","9:05 PM"));
        callHistoryModelArrayList.add(new CallHistoryModel("Bob","9:04 mins","9:05 PM"));
        callHistoryModelArrayList.add(new CallHistoryModel("Bob","9:04 mins","9:05 PM"));
        callHistoryModelArrayList.add(new CallHistoryModel("Bob","9:04 mins","9:05 PM"));
        callHistoryModelArrayList.add(new CallHistoryModel("Bob","9:04 mins","9:05 PM"));
        callHistoryModelArrayList.add(new CallHistoryModel("Bob","9:04 mins","9:05 PM"));
        callHistoryModelArrayList.add(new CallHistoryModel("Bob","9:04 mins","9:05 PM"));
        callHistoryModelArrayList.add(new CallHistoryModel("Bob","9:04 mins","9:05 PM"));
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