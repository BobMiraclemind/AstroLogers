package com.astroexpress.astrologer.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.databinding.ActivityContactUsBinding;

public class ContactUsActivity extends AppCompatActivity {

    ActivityContactUsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar));
        binding = ActivityContactUsBinding.inflate(getLayoutInflater());
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

        binding.callNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "7678350493"));
//                startActivity(intent);
            }
        });

        binding.emailNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_SENDTO);
//                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//                intent.putExtra(Intent.EXTRA_EMAIL, "support@astroexpress.in");
//                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(intent);
//                }
            }
        });
    }
}