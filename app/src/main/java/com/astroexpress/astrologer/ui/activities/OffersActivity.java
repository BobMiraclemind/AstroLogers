package com.astroexpress.astrologer.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.adapter.OffersAdapter;
import com.astroexpress.astrologer.databinding.ActivityOffersBinding;
import com.astroexpress.astrologer.models.response.ApplyOfferResponseModel;
import com.astroexpress.astrologer.models.response.OfferResponseModel;
import com.astroexpress.astrologer.network.RetrofitClient;
import com.astroexpress.astrologer.utils.AllStaticMethods;
import com.astroexpress.astrologer.utils.AppConstants;
import com.astroexpress.astrologer.utils.NetworkStats;
import com.astroexpress.astrologer.utils.StaticFields;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OffersActivity extends AppCompatActivity {

    ActivityOffersBinding binding;
    OffersAdapter offersAdapter;
    List<OfferResponseModel.Result> list;
    NetworkStats networkStats = new NetworkStats();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar));
        binding = ActivityOffersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.left_back);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        callOffersApi();

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

    private void callOffersApi() {
        RetrofitClient.getApiClient().getOffersList(StaticFields.astrologerData.getAstrologerId()).enqueue(new Callback<OfferResponseModel>() {
            @Override
            public void onResponse(Call<OfferResponseModel> call, Response<OfferResponseModel> response) {
                try {
                    if (response.isSuccessful() && response.body() != null){
                        if (response.body().getCode().equals("200")){
                            binding.statusMsg.setVisibility(View.GONE);
                            offersAdapter = new OffersAdapter(getApplicationContext(),response.body().getResult());
                            binding.offerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
                            binding.offerView.setAdapter(offersAdapter);
                            list = response.body().getResult();
                        }else {
                            binding.statusMsg.setVisibility(View.VISIBLE);
                            binding.statusMsg.setText(response.body().getMessage());
                        }
                    }
                }catch (Exception e){
                    AllStaticMethods.saveException(e);
                }
            }

            @Override
            public void onFailure(Call<OfferResponseModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), AppConstants.TOAST_MESSAGES,Toast.LENGTH_SHORT).show();
            }
        });
    }
}