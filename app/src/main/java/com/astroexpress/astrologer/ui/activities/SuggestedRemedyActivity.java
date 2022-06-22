package com.astroexpress.astrologer.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.adapter.SuggestedRemedyAdapter;
import com.astroexpress.astrologer.databinding.ActivitySuggestedRemedyBinding;
import com.astroexpress.astrologer.models.response.SuggestedRemedyModel;
import com.astroexpress.astrologer.network.RetrofitClient;
import com.astroexpress.astrologer.utils.AllStaticMethods;
import com.astroexpress.astrologer.utils.StaticFields;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuggestedRemedyActivity extends AppCompatActivity {

    ActivitySuggestedRemedyBinding binding;
    SuggestedRemedyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySuggestedRemedyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.left_back);
        binding.toolbar.setNavigationOnClickListener(view -> onBackPressed());

        callSuggestedRemedyApi();
    }

    private void callSuggestedRemedyApi() {
        RetrofitClient.getApiClient().getSuggestedRemedy(StaticFields.astrologerData.getAstrologerId(),"0").enqueue(new Callback<SuggestedRemedyModel>() {
            @Override
            public void onResponse(Call<SuggestedRemedyModel> call, Response<SuggestedRemedyModel> response) {
                try {
                    if (response.isSuccessful() && response.body() != null){
                        if (response.body().getCode().equals("200")){
                            binding.statusMsg.setVisibility(View.GONE);
                            binding.suggestRemedyList.setVisibility(View.VISIBLE);
                            adapter = new SuggestedRemedyAdapter(getApplicationContext(),response.body().getResult());
                            binding.suggestRemedyList.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
                            binding.suggestRemedyList.setAdapter(adapter);
                        }else {
                            binding.statusMsg.setVisibility(View.VISIBLE);
                            binding.suggestRemedyList.setVisibility(View.GONE);
                            binding.statusMsg.setText(response.body().getMessage());
                        }
                    }else {

                    }
                }catch (Exception e){
                    AllStaticMethods.saveException(e);
                }

            }

            @Override
            public void onFailure(Call<SuggestedRemedyModel> call, Throwable t) {

            }
        });
    }
}