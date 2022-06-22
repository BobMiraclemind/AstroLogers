package com.astroexpress.astrologer.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.databinding.ActivitySignInBinding;
import com.astroexpress.astrologer.models.response.LoginsModel;
import com.astroexpress.astrologer.network.RetrofitClient;
import com.astroexpress.astrologer.utils.AppConstants;
import com.astroexpress.astrologer.utils.NetworkStats;
import com.astroexpress.astrologer.utils.SharedPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    ActivitySignInBinding binding;
    NetworkStats networkStats = new NetworkStats();

    boolean passwordVisible;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar));
        setTheme(R.style.Theme_AstroLogers);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.username.setOnTouchListener((view, motionEvent) -> {
            binding.signupStatus.setVisibility(View.GONE);
            return false;
        });
        binding.password.setOnTouchListener((view, motionEvent) -> {
            binding.signupStatus.setVisibility(View.GONE);
            return false;
        });
        binding.username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                /*if (charSequence.length() ==  0 ){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        binding.username.setTypeface(getResources().getFont(R.font.simplo_it));
                    }
                }else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        binding.username.setTypeface(getResources().getFont(R.font.simplo_regular));
                    }
//                    binding.username.clearComposingText();
                }*/
            }

            @Override
            public void afterTextChanged(Editable editable) {
//                binding.username.clearComposingText();
            }
        });
        binding.password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                /*if (charSequence.length() == 0){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        binding.password.setTypeface(getResources().getFont(R.font.simplo_it));
                    }
                }else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        binding.password.setTypeface(getResources().getFont(R.font.simplo_regular));
                    }
                }*/
            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.password.clearComposingText();
            }
        });
        binding.password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if (motionEvent.getRawX()>=binding.password.getRight()-binding.password.getCompoundDrawables()[2].getBounds().width()){
                        int selection = binding.password.getSelectionEnd();
                        if (passwordVisible){
                            binding.password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.password_toggle_off,0);
                            binding.password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        }else {
                            binding.password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.password_toggle_on,0);
                            binding.password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }
                        binding.password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        binding.login.setOnClickListener(view -> {
            binding.progress.setVisibility(View.VISIBLE);
            checkForValidation();
        });

    }

    private void checkForValidation() {

        if (binding.username.getText().toString().isEmpty()){
            binding.username.setError("Username required");
            binding.username.requestFocus();
            binding.progress.setVisibility(View.GONE);

        }else {
            if (!Patterns.EMAIL_ADDRESS.matcher(binding.username.getText().toString()).matches()) {
                binding.username.setError("Invalid Username");
                binding.username.requestFocus();
                binding.progress.setVisibility(View.GONE);
            }
        }
        if (binding.password.getText().toString().isEmpty()){
            binding.password.setError("Password required");
            binding.password.requestFocus();
            binding.progress.setVisibility(View.GONE);
        }
        if (!binding.username.getText().toString().isEmpty() &&
                !binding.password.getText().toString().isEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(binding.username.getText().toString()).matches()){
            loginWithCredentials(binding.username.getText().toString(),binding.password.getText().toString());
        }
    }

    private void loginWithCredentials(String email, String password) {
        RetrofitClient.getApiClient().login(email,password).enqueue(new Callback<LoginsModel>() {
            @Override
            public void onResponse(Call<LoginsModel> call, Response<LoginsModel> response) {
                try {
                    if (response.isSuccessful() && response.body() != null){
                        if (response.body().getCode().equals("200")){
                            binding.progress.setVisibility(View.GONE);
                            SharedPreferenceManager.setUserData(getApplicationContext(),response.body().getResult());
                            startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                            finish();
                        }else {
                            binding.progress.setVisibility(View.GONE);
                            binding.signupStatus.setText(response.body().getMessage());
                            binding.signupStatus.setVisibility(View.VISIBLE);
                        }
                    }else {
                        Toast.makeText(SignInActivity.this, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<LoginsModel> call, Throwable t) {
                binding.signupStatus.setText(t.getMessage());
                binding.progress.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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