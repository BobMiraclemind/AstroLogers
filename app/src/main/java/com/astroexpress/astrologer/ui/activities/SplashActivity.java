package com.astroexpress.astrologer.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.astroexpress.astrologer.databinding.ActivitySplashBinding;
import com.astroexpress.astrologer.models.response.LoginsModel;
import com.astroexpress.astrologer.models.response.StatusModel;
import com.astroexpress.astrologer.network.RetrofitClient;
import com.astroexpress.astrologer.utils.AppConstants;
import com.astroexpress.astrologer.utils.SharedPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;

    private static String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (SharedPreferenceManager.getUserData(getApplicationContext()) == null ){
            startActivity(new Intent(getApplicationContext(),SignInActivity.class));
            finish();
        }else {
//            loginWithCredentials(SharedPreferenceManager.getUserData(getApplicationContext()).getEmail(),SharedPreferenceManager.getUserData(getApplicationContext()).getPassword());
            startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
            finish();
        }
    }

    private void loginWithCredentials(String email, String password) {
        RetrofitClient.getApiClient().login(email,password).enqueue(new Callback<LoginsModel>() {
            @Override
            public void onResponse(Call<LoginsModel> call, Response<LoginsModel> response) {
                try {
                    if (response.isSuccessful() && response.body() != null){
                        if (response.body().getCode().equals("200")){
//                            binding.progress.setVisibility(View.GONE);
                            SharedPreferenceManager.setUserData(getApplicationContext(),response.body().getResult());
                            startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
//                            binding.progress.setVisibility(View.GONE);
//                            binding.signupStatus.setText(response.message());
//                            binding.signupStatus.setVisibility(View.VISIBLE);
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<LoginsModel> call, Throwable t) {
//                binding.signupStatus.setText(t.getMessage());
//                binding.progress.setVisibility(View.GONE);
            }
        });
    }
}