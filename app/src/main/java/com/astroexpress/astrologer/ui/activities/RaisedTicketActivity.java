package com.astroexpress.astrologer.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.adapter.RaisedTicketAdapter;
import com.astroexpress.astrologer.databinding.ActivityRaisedTicketBinding;
import com.astroexpress.astrologer.databinding.RaisedTicketBottomSheetLayoutBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class RaisedTicketActivity extends AppCompatActivity {

    ActivityRaisedTicketBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRaisedTicketBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.left_back);
        binding.toolbar.setNavigationOnClickListener(view -> onBackPressed());

        RaisedTicketAdapter raisedTicketAdapter = new RaisedTicketAdapter(getApplicationContext());
        binding.raisedTicketView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        binding.raisedTicketView.setAdapter(raisedTicketAdapter);

        binding.sort.setOnClickListener(view -> bottomSheet());

    }

    private void bottomSheet() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.raised_ticket_bottom_sheet_layout);
        bottomSheetDialog.findViewById(R.id.all).setOnClickListener(view -> {
            binding.sort.setText(R.string.sort_by_all);
            bottomSheetDialog.dismiss();
        });
        bottomSheetDialog.findViewById(R.id.pending).setOnClickListener(view -> {
            binding.sort.setText(R.string.sort_by_pending);
            bottomSheetDialog.dismiss();
        });
        bottomSheetDialog.findViewById(R.id.solved).setOnClickListener(view -> {
            binding.sort.setText(R.string.sort_by_solved);
            bottomSheetDialog.dismiss();
        });
        bottomSheetDialog.show();
    }

}