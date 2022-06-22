package com.astroexpress.astrologer.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.adapter.BannerSlideAdapter;
import com.astroexpress.astrologer.adapter.BlogsAdapter;
import com.astroexpress.astrologer.adapter.NextOnlineTimeAdapter;
import com.astroexpress.astrologer.adapter.RecentUserAdapter;
import com.astroexpress.astrologer.databinding.ActivityDashboardBinding;
import com.astroexpress.astrologer.databinding.NavigationHeaderLayoutBinding;
import com.astroexpress.astrologer.databinding.TimerDialogLayoutBinding;
import com.astroexpress.astrologer.models.request.SaveNextOnlineTimeRequestModel;
import com.astroexpress.astrologer.models.response.AstrologerStatusModel;
import com.astroexpress.astrologer.models.response.BannersModel;
import com.astroexpress.astrologer.models.response.BoostModel;
import com.astroexpress.astrologer.models.response.ChatRunningSessionResponseModel;
import com.astroexpress.astrologer.models.response.LoginsModel;
import com.astroexpress.astrologer.models.response.NextOnlineResponseModel;
import com.astroexpress.astrologer.models.response.NotifyUsersResponseModel;
import com.astroexpress.astrologer.models.response.SaveNextOnlineTimeResponseModel;
import com.astroexpress.astrologer.models.response.StatusModel;
import com.astroexpress.astrologer.models.response.TestimonialModels;
import com.astroexpress.astrologer.models.response.UsersModel;
import com.astroexpress.astrologer.network.RetrofitClient;
import com.astroexpress.astrologer.services.NotificationService;
import com.astroexpress.astrologer.utils.AllStaticMethods;
import com.astroexpress.astrologer.utils.AppConstants;
import com.astroexpress.astrologer.utils.MyNotification;
import com.astroexpress.astrologer.utils.NetworkStats;
import com.astroexpress.astrologer.utils.SharedPreferenceManager;
import com.astroexpress.astrologer.utils.StaticFields;
import com.astroexpress.astrologer.utils.Utilities;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,Toolbar.OnMenuItemClickListener {

    private static final String TAG = "Bobb";
    ActivityDashboardBinding binding;
    BannerSlideAdapter bannerSlideAdapter;
    RecentUserAdapter recentUserAdapter;
    BlogsAdapter blogsAdapter;
    Timer timer;
    DatabaseReference dbRefStatus;
    public static Activity dashboardActivity;
    boolean isOnlineCall,isOnlineChat,isOnlineChatEm ,isOnlineCallEm ;
    NotifyUsersResponseModel.Result userData;
    NetworkStats networkStats = new NetworkStats();
    ChatRunningSessionResponseModel.Result sessionResult;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AstroLogers);
//        getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar));
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dashboardActivity = this;

        callSignInApi();

        Animatable animatable = (Animatable) binding.backToChat.getDrawable();
        animatable.start();
        binding.backToChat.setVisibility(View.GONE);

        if (Utilities.isServiceRunning(DashboardActivity.this, NotificationService.class)){
            Log.i(TAG, "onCreate: Service running");
        }else {
            startService(new Intent(getApplicationContext(),NotificationService.class));
            Log.i(TAG, "onCreate: Service Started");
        }

        binding.backToChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitClient.getApiClient().getUserForRequest(sessionResult.getUserId()).enqueue(new Callback<NotifyUsersResponseModel>() {
                    @Override
                    public void onResponse(Call<NotifyUsersResponseModel> call, Response<NotifyUsersResponseModel> response) {
                        try {
                            if (response.body()!=null && response.isSuccessful()){
                                if (response.body().getCode().equals("200")){
                                    Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
                                    intent.putExtra("userId",sessionResult.getUserId());
                                    intent.putExtra("astrologerId",sessionResult.getAstrologerId());
                                    intent.putExtra("sessionId",sessionResult.getSessionId());
                                    intent.putExtra("username",response.body().getResult().getFirstname()+" "+response.body().getResult().getLastname());
                                    startActivity(intent);
                                }else {
                                    Log.i(TAG, "onResponse: "+response.body().getMessage());
//                                    Toast.makeText(DashboardActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(getApplicationContext(),AppConstants.TOAST_MESSAGES,Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            AllStaticMethods.saveException(e);
                        }
                    }

                    @Override
                    public void onFailure(Call<NotifyUsersResponseModel> call, Throwable t) {
                        Toast.makeText(DashboardActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        /*dbRefStatus = FirebaseDatabase.getInstance().getReference("Chats")
                .child("Astrologer")
                .child(SharedPreferenceManager.getUserData(getApplicationContext()).getAstrologerId())
                .child("Status");

        dbRefStatus.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("RequestToAstrologer").getValue() != null && snapshot.child("RequestToAstrologer").getValue().toString().equals("Calling")) {
                    if (snapshot.child("UserId").getValue() != null) {

                        RetrofitClient.getApiClient().getUserForRequest(snapshot.child("UserId").getValue().toString()).enqueue(new Callback<NotifyUsersResponseModel>() {
                            @Override
                            public void onResponse(Call<NotifyUsersResponseModel> call, Response<NotifyUsersResponseModel> response) {
                                userData = response.body().getResult();
                                String fullname = userData.getFirstname()+" "+userData.getLastname();
                                MyNotification.createNotification(getApplicationContext(),
                                        Integer.parseInt(snapshot.child("UserId").getValue().toString()),
                                        snapshot.child("UserId").getValue().toString(),
                                        SharedPreferenceManager.getUserData(getApplicationContext()).getAstrologerId(),
                                        fullname,
                                        "wants to chat with you");
                            }

                            @Override
                            public void onFailure(Call<NotifyUsersResponseModel> call, Throwable t) {

                            }
                        });
//                        userId = snapshot.child("UserId").getValue().toString();
//                        MyNotificationManager.sendNewChatRequestNotification(getApplicationContext(),Integer.parseInt(snapshot.child("UserId").getValue().toString()),  snapshot.child("UserId").getValue().toString(), StaticFields.astrologerData.getAstrologerId(), " User", "Wants to chat with you");
                        *//*getDataRequestUser(snapshot.child("UserId").getValue().toString());
                        MyNotification.createNotification(getApplicationContext(),
                                Integer.parseInt(snapshot.child("UserId").getValue().toString()),
                                snapshot.child("UserId").getValue().toString(),
                                SharedPreferenceManager.getUserData(getApplicationContext()).getAstrologerId(),
                                " User",
                                "Wants to chat with you");*//*
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        getAstrologerStatus();
        getNextOnlineTime();


        binding.boost.setOnTouchListener((view, motionEvent) -> {
            // show interest in events resulting from ACTION_DOWN
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) return true;

            // don't handle event unless its ACTION_UP so "doSomething()" only runs once.
            if (motionEvent.getAction() != MotionEvent.ACTION_UP) return false;

//                doSomething();
            if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                if (binding.boost.isPressed()){
                    binding.boost.setPressed(false);
                        Toast.makeText(getApplicationContext(),"Deactivated",Toast.LENGTH_SHORT).show();
                    setBoost("false");
                }else {
                    binding.boost.setPressed(true);
                        Toast.makeText(getApplicationContext(),"Activated",Toast.LENGTH_SHORT).show();
                    setBoost("true");
                }
                return true;
            }
            return true;
        });


        setSupportActionBar(binding.toolbar);
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,binding.drawerLayout,binding.toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerToggle.setDrawerIndicatorEnabled(false);
        drawerToggle.setHomeAsUpIndicator(R.drawable.dashboard_hamburger);
        drawerToggle.setToolbarNavigationClickListener(view -> binding.drawerLayout.openDrawer(GravityCompat.START));
//        binding.toolbar.inflateMenu(R.menu.main_menu);
//        binding.toolbar.setOnMenuItemClickListener(this);
        binding.navView.setNavigationItemSelectedListener(this);

        binding.chat.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),ChatUsersListActivity.class)));

        getBannerData();
        binding.bannerSlider.setClipToPadding(false);
        binding.bannerSlider.setPadding(50,0,50,    0);
//        binding.bannerSlider.setPageMargin(20);
        binding.bannerTab.setupWithViewPager(binding.bannerSlider,true);
        getDataForRecentUser();
        getDataForBlogs();
        setTimeState();

        binding.cardView.setOnClickListener(view -> Toast.makeText(getApplicationContext(),"Turns off after 5 sessions of the day",Toast.LENGTH_LONG).show());
        binding.navView.getHeaderView(0).findViewById(R.id.close_nav).setOnClickListener(view -> binding.drawerLayout.closeDrawers());
        binding.navView.getHeaderView(0).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),AstroProfileActivity.class));
            binding.drawerLayout.closeDrawers();
        });
        binding.call.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), CallHistoryActivity.class)));
        binding.offer.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),OffersActivity.class)));
        binding.seeAllRecent.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),RecentInteractionActivity.class)));
        binding.report.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),ReportsActivity.class)));
        binding.waitlist.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), WaitListActivity.class)));
        binding.wallet.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),WalletActivity.class)));

        if (binding.chatSwitch.isChecked()){
            binding.chatState.setText("0N");
        }else {
            binding.chatState.setText("OFF");
        }
        if (binding.callSwitch.isChecked()){
            binding.callState.setText("0N");
        }else {
            binding.callState.setText("OFF");
        }
        if (binding.chatEmergencySwitch.isChecked()){
            binding.emChatState.setText("0N");
        }else {
            binding.emChatState.setText("OFF");
        }
        if (binding.callEmergencySwitch.isChecked()){
            binding.emCallState.setText("0N");
        }else {
            binding.emCallState.setText("OFF");
        }
        binding.chatSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                isOnlineChat = true;
                binding.chatState.setText("0N");
            }else {
                isOnlineChat = false;
                binding.chatState.setText("0FF");
            }
            updateStatus(isOnlineChat,isOnlineCall,isOnlineChatEm,isOnlineCallEm);
        });
        binding.callSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                isOnlineCall = true;
                binding.callState.setText("0N");
            }else {
                isOnlineCall = false;
                binding.callState.setText("OFF");
            }
            updateStatus(isOnlineChat,isOnlineCall,isOnlineChatEm,isOnlineCallEm);
        });
        binding.chatEmergencySwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                isOnlineChatEm = true;
                binding.emChatState.setText("0N");
            }else {
                isOnlineChatEm = false;
                binding.emChatState.setText("OFF");
            }
            updateStatus(isOnlineChat,isOnlineCall,isOnlineChatEm,isOnlineCallEm);
        });
        binding.callEmergencySwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                isOnlineCallEm = true;
                binding.emCallState.setText("0N");
            }else {
                isOnlineCallEm = false;
                binding.emCallState.setText("OFF");
            }
            updateStatus(isOnlineChat,isOnlineCall,isOnlineChatEm,isOnlineCallEm);
        });

        setDataOnFields();

    }

    private void callSignInApi() {
        RetrofitClient.getApiClient().login(SharedPreferenceManager.getUserData(getApplicationContext()).getEmail(),SharedPreferenceManager.getUserData(getApplicationContext()).getPassword()).enqueue(new Callback<LoginsModel>() {
            @Override
            public void onResponse(Call<LoginsModel> call, Response<LoginsModel> response) {
                try {
                    if (response.body()!=null && response.isSuccessful()){
                        if (response.body().getCode().equals("200")){
                            StaticFields.astrologerData = response.body().getResult();
                            SharedPreferenceManager.setUserData(getApplicationContext(),response.body().getResult());
                        }else {
                            Toast.makeText(DashboardActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(DashboardActivity.this, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    AllStaticMethods.saveException(e);
                }
            }

            @Override
            public void onFailure(Call<LoginsModel> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAstrologerStatus() {
        RetrofitClient.getApiClient().getAstrologerStatus(SharedPreferenceManager.getUserData(getApplicationContext()).getAstrologerId()).enqueue(new Callback<AstrologerStatusModel>() {
            @Override
            public void onResponse(@NonNull Call<AstrologerStatusModel> call, @NonNull Response<AstrologerStatusModel> response) {
                AstrologerStatusModel.Result data = response.body() != null ? response.body().getResult() : null;
                try {
                    if (response.body()!=null && response.isSuccessful()){
                        if (response.body().getCode().equals("200")){
                            if (!data.isActive()){
                                startActivity(new Intent(getApplicationContext(),SignInActivity.class));
                                finish();
                            }
                            if (data.isDeleted()){
                                startActivity(new Intent(getApplicationContext(),SignInActivity.class));
                                finish();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),AppConstants.TOAST_MESSAGES,Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    AllStaticMethods.saveException(e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AstrologerStatusModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataRequestUser(String userID){
        RetrofitClient.getApiClient().getUserForRequest(userID).enqueue(new Callback<NotifyUsersResponseModel>() {
            @Override
            public void onResponse(Call<NotifyUsersResponseModel> call, Response<NotifyUsersResponseModel> response) {
                try {
                    if (response.body()!=null && response.isSuccessful()){
                        if (response.body().getCode().equals("200")){
                            userData = response.body().getResult();
                        }else {
                            Toast.makeText(DashboardActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(DashboardActivity.this, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    AllStaticMethods.saveException(e);
                }
            }

            @Override
            public void onFailure(Call<NotifyUsersResponseModel> call, Throwable t) {

            }
        });
    }

    private void updateStatus(boolean isOnlineChat,boolean isOnlineCall,boolean isOnlineChatEm,boolean isOnlineCallEm) {

        RetrofitClient.getApiClient().statusUpdate(SharedPreferenceManager.getUserData(getApplicationContext()).getAstrologerId(),Boolean.toString(isOnlineChat),Boolean.toString(isOnlineCall),Boolean.toString(isOnlineChatEm),Boolean.toString(isOnlineCallEm)).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                try {
                    if (response.body()!=null && response.isSuccessful()){
                        if (response.body().getCode().equals("200")){
                            Log.i(TAG, "onResponse: "+response.body().getResult());
                            SharedPreferenceManager.saveStatus(getApplicationContext(),response.body().getResult());
                        }else {
                            Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),AppConstants.TOAST_MESSAGES,Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    AllStaticMethods.saveException(e);
                }
            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getBannerData() {
        RetrofitClient.getApiClient().banner().enqueue(new Callback<BannersModel>() {
            @Override
            public void onResponse(Call<BannersModel> call, Response<BannersModel> response) {
                try {
                    if (response.isSuccessful() && response.body() != null){
                        if (response.body().getCode().equals("200")){
                            binding.bannerSlider.setVisibility(View.VISIBLE);
                            binding.bannerTab.setVisibility(View.VISIBLE);
                            bannerSlideAdapter = new BannerSlideAdapter(getApplicationContext(),response.body().getResult());
                            binding.bannerSlider.setAdapter(bannerSlideAdapter);
                            setBannerLooper();
                        }else {
                            Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),AppConstants.TOAST_MESSAGES,Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    AllStaticMethods.saveException(e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BannersModel> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDataOnFields() {


        if (SharedPreferenceManager.getUserData(getApplicationContext()) != null){
            View headerView = binding.navView.getHeaderView(0);
            NavigationHeaderLayoutBinding headerLayoutBinding = NavigationHeaderLayoutBinding.bind(headerView);
            binding.firstname.setText(SharedPreferenceManager.getUserData(getApplicationContext()).getFirstname()==null?"":SharedPreferenceManager.getUserData(getApplicationContext()).getFirstname());
            binding.lastname.setText(SharedPreferenceManager.getUserData(getApplicationContext()).getLastname()==null?"":SharedPreferenceManager.getUserData(getApplicationContext()).getLastname());
            Glide.with(getApplicationContext())
                    .load(SharedPreferenceManager.getUserData(getApplicationContext()).getProfileImgUrl())
                    .centerCrop()
                    .placeholder(R.drawable.place_holder)
                    .into(binding.profileImg);
            headerLayoutBinding.firstname.setText(SharedPreferenceManager.getUserData(getApplicationContext()).getFirstname()==null?"":SharedPreferenceManager.getUserData(getApplicationContext()).getFirstname());
            headerLayoutBinding.lastname.setText(SharedPreferenceManager.getUserData(getApplicationContext()).getLastname()==null?"":SharedPreferenceManager.getUserData(getApplicationContext()).getLastname());
            Glide.with(getApplicationContext())
                    .load(SharedPreferenceManager.getUserData(getApplicationContext()).getProfileImgUrl())
                    .centerCrop()
                    .placeholder(R.drawable.place_holder)
                    .into(headerLayoutBinding.profileImg);
            headerLayoutBinding.rating.setText(SharedPreferenceManager.getUserData(getApplicationContext()).getRating()==null?"":SharedPreferenceManager.getUserData(getApplicationContext()).getRating());
            headerLayoutBinding.ratingBar.setRating(Float.parseFloat(SharedPreferenceManager.getUserData(getApplicationContext()).getRating()==null?"":SharedPreferenceManager.getUserData(getApplicationContext()).getRating()));
        }
        if (SharedPreferenceManager.getUserData(getApplicationContext())!=null){
            Log.i(TAG, "setDataOnFields: "+SharedPreferenceManager.getUserData(dashboardActivity).getIsBoosted());
            if (SharedPreferenceManager.getUserData(getApplicationContext()).getIsBoosted().equals("true")){
                binding.boost.setPressed(true);
            }else {
                binding.boost.setPressed(false);
            }
        }
        if(SharedPreferenceManager.getStatus(getApplicationContext()) != null){
            binding.chatSwitch.setChecked(SharedPreferenceManager.getStatus(getApplicationContext()).isOnlineForChat());
            binding.callSwitch.setChecked(SharedPreferenceManager.getStatus(getApplicationContext()).isOnlineForCall());
            binding.chatEmergencySwitch.setChecked(SharedPreferenceManager.getStatus(getApplicationContext()).isOnlineForChatEM());
            binding.callEmergencySwitch.setChecked(SharedPreferenceManager.getStatus(getApplicationContext()).isOnlineForCallEM());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTimeState();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkStats,intentFilter);
        callChatRunningStatus();
//        startService(new Intent(getApplicationContext(), NotificationService.class));
    }

    private void callChatRunningStatus() {
        RetrofitClient.getApiClient().getChatRunningSession(SharedPreferenceManager.getUserData(DashboardActivity.dashboardActivity).getAstrologerId()).enqueue(new Callback<ChatRunningSessionResponseModel>() {
            @Override
            public void onResponse(Call<ChatRunningSessionResponseModel> call, Response<ChatRunningSessionResponseModel> response) {
                try {
                    if (response.isSuccessful() && response.body() != null){
                        if (response.body().getCode().equals("200")){
                            binding.backToChat.setVisibility(View.VISIBLE);
                            sessionResult = response.body().getResult();
                        }else {
                            binding.backToChat.setVisibility(View.GONE);
                            Log.i(TAG, "onResponse: "+response.body().getMessage());
//                            Toast.makeText(DashboardActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), AppConstants.TOAST_MESSAGES,Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    AllStaticMethods.saveException(e);
                }
            }

            @Override
            public void onFailure(Call<ChatRunningSessionResponseModel> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SharedPreferenceManager.getUserData(getApplicationContext())!=null){
            Log.i(TAG, "setDataOnFields: "+SharedPreferenceManager.getUserData(dashboardActivity).getIsBoosted());
            if (SharedPreferenceManager.getUserData(getApplicationContext()).getIsBoosted().equals("true")){
                binding.boost.setPressed(true);
            }else {
                binding.boost.setPressed(false);
            }
        }
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkStats);
        super.onStop();
    }

    private void getDataForBlogs() {
        RetrofitClient.getApiClient().testimonial().enqueue(new Callback<TestimonialModels>() {
            @Override
            public void onResponse(Call<TestimonialModels> call, Response<TestimonialModels> response) {
                try {
                    if (response.isSuccessful() && response.body() != null){
                        if (response.body().getCode().equals("200")){
                            binding.blogsView.setVisibility(View.VISIBLE);
                            binding.blogsTitle.setVisibility(View.VISIBLE);
                            binding.seeAllBlog.setVisibility(View.VISIBLE);
                            blogsAdapter = new BlogsAdapter(getApplicationContext(),response.body().getResult());
                            binding.blogsView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                            binding.blogsView.setAdapter(blogsAdapter);
                        }else {
                            Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();}
                    }else {
                        Toast.makeText(getApplicationContext(),AppConstants.TOAST_MESSAGES,Toast.LENGTH_SHORT).show();}
                }catch (Exception e){
                    AllStaticMethods.saveException(e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TestimonialModels> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setTimeState() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            binding.timeState.setText("Good Morning");
            binding.timeState.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.time_morning,0);
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            binding.timeState.setText("Good Afternoon");
            binding.timeState.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.time_noon,0);
        }else if(timeOfDay >= 16 && timeOfDay < 24){
            binding.timeState.setText("Good Evening");
            binding.timeState.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.time_evening,0);
        }
    }

    private void setBannerLooper() {
        final long DELAY_MS = 1000;//delay in milliseconds before task is to be executed
        final long PERIOD_MS = 5000; // time in milliseconds between successive task executions.
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                if (binding.bannerSlider.getCurrentItem() == bannerSlideAdapter.getCount() - 1) { //adapter is your custom ViewPager's adapter
                    binding.bannerSlider.setCurrentItem(0,false);
                }
                else {
                    binding.bannerSlider.setCurrentItem(binding.bannerSlider.getCurrentItem() + 1, true);
                }
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    private void getDataForRecentUser() {
        RetrofitClient.getApiClient().recentUsers(SharedPreferenceManager.getUserData(getApplicationContext()).getAstrologerId()).enqueue(new Callback<UsersModel>() {
            @Override
            public void onResponse(Call<UsersModel> call, Response<UsersModel> response) {
                try {
                    if (response.isSuccessful() && response.body() != null){
                        if (response.body().getCode().equals("200")){
                            binding.recentUsersView.setVisibility(View.VISIBLE);
                            binding.recentUsersTitle.setVisibility(View.VISIBLE);
                            binding.seeAllRecent.setVisibility(View.VISIBLE);
                            recentUserAdapter = new RecentUserAdapter(getApplicationContext(),response.body().getResult());
                            binding.recentUsersView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                            binding.recentUsersView.setAdapter(recentUserAdapter);
                        }else {
                            Log.i(TAG, "onResponse: getRecentUsers"+response.body().getMessage());
                            Toast.makeText(DashboardActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), AppConstants.TOAST_MESSAGES,Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    AllStaticMethods.saveException(e);
                }
            }

            @Override
            public void onFailure(Call<UsersModel> call, Throwable t) {

            }
        });
    }

    private void setBoost(String isEnabled){
        RetrofitClient.getApiClient().setBoost(SharedPreferenceManager.getUserData(getApplicationContext()).getAstrologerId(),isEnabled).enqueue(new Callback<BoostModel>() {
            @Override
            public void onResponse(Call<BoostModel> call, Response<BoostModel> response) {
                try {
                    if (response.isSuccessful() && response.body() != null){
                        if (response.body().getCode().equals("200")){
                            callSignInApi();
                            String result = response.body().getResult().getIsEnabled();
                            if (result.matches("true")){
                                Log.i(TAG, "onResponse: "+result);
                                Toast.makeText(getApplicationContext(),"Boost Activated",Toast.LENGTH_SHORT).show();
                            }else {
                                Log.i(TAG, "onResponse: "+result);
                                Toast.makeText(getApplicationContext(),"Boost Deactivated",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            if (isEnabled.matches("true")){
                                binding.boost.setPressed(true);
                            }else {
                                binding.boost.setPressed(false);
                            }
                            Log.i(TAG, "onResponse: setBoost "+response.body().getMessage());
                            Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), AppConstants.TOAST_MESSAGES,Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    AllStaticMethods.saveException(e);
                }
            }

            @Override
            public void onFailure(Call<BoostModel> call, Throwable t) {
                binding.boost.setPressed(false);
                Toast.makeText(DashboardActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                break;
            case R.id.wallet:
                startActivity(new Intent(getApplicationContext(),WalletActivity.class));
                break;
            case R.id.myProfile:
                startActivity(new Intent(getApplicationContext(),AstroProfileActivity.class));
                break;
            case R.id.followers:
                startActivity(new Intent(getApplicationContext(),FollowersActivity.class));
                break;
            case R.id.chat_history:
                startActivity(new Intent(getApplicationContext(),ChatHistoryActivity.class));
                break;
            case  R.id.call_history:
                startActivity(new Intent(getApplicationContext(),CallDetailActivity.class));
                break;
            case R.id.suggested_remedy:
                startActivity(new Intent(getApplicationContext(),SuggestedRemedyActivity.class));
                break;
            /*case R.id.suggest_remedy:
                startActivity(new Intent(getApplicationContext(),SuggestRemedyActivity.class));
                break;*/
            /*case R.id.notifications:
                startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
                break;*/
            /*case R.id.my_orders:
                startActivity(new Intent(getApplicationContext(),MyOrdersActivity.class));
                break;*/
            /*case R.id.astromarket:
                startActivity(new Intent(getApplicationContext(),AstroMarketActivity.class));
                break;*/
            /*case R.id.report:
                startActivity(new Intent(getApplicationContext(),ReportsActivity.class));
                break;*/
            case R.id.setting:
                startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
                break;
            default:
                binding.drawerLayout.closeDrawers();
                return false;
        }
        binding.drawerLayout.closeDrawers();
        return false;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.notification){
            startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    String callTime,chatTime,chatDate,callDate;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.timer:
                Date date = new Date();
                TimerDialogLayoutBinding dialogLayoutBinding = TimerDialogLayoutBinding.inflate(getLayoutInflater());
                dialogLayoutBinding.chatTime.setText(new SimpleDateFormat("hh:mm a").format(date));
                dialogLayoutBinding.callTime.setText(new SimpleDateFormat("hh:mm a").format(date));
                dialogLayoutBinding.chatDate.setText(new SimpleDateFormat("dd MMM,EEE").format(date));
                dialogLayoutBinding.callDate.setText(new SimpleDateFormat("dd MMM,EEE").format(date));

                try {
                    Date chat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse(StaticFields.nextOnlineTime.getNextOnlineForChat()==null?"0":StaticFields.nextOnlineTime.getNextOnlineForChat());
                    Date call = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse(StaticFields.nextOnlineTime.getNextOnlineForCall()==null?"0":StaticFields.nextOnlineTime.getNextOnlineForCall());

                    Toast.makeText(dashboardActivity, chat.toString(), Toast.LENGTH_SHORT).show();

                    dialogLayoutBinding.chatTimeView.setText(new SimpleDateFormat("hh:mm a").format(chat));
                    dialogLayoutBinding.callTimeView.setText(new SimpleDateFormat("hh:mm a").format(call));
                    dialogLayoutBinding.chatDateView.setText(new SimpleDateFormat("dd MMM,EEE").format(chat));
                    dialogLayoutBinding.callDateView.setText(new SimpleDateFormat("dd MMM,EEE").format(call));
                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.e(TAG, "onOptionsItemSelected: ", e);
                }
                Dialog dialog = new Dialog(this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(dialogLayoutBinding.getRoot());
                dialog.setCancelable(false);
                dialog.show();
                dialogLayoutBinding.close.setOnClickListener(view -> dialog.dismiss());
                dialogLayoutBinding.chat.setOnClickListener(view -> {
                    dialogLayoutBinding.chat.setShapeType(1);
                    dialogLayoutBinding.call.setShapeType(0);
                    dialogLayoutBinding.chatLayout.setVisibility(View.VISIBLE);
                    dialogLayoutBinding.callLayout.setVisibility(View.GONE);
                    dialogLayoutBinding.chat.setBackgroundColor(getResources().getColor(R.color.base_blue));
                    dialogLayoutBinding.call.setBackgroundColor(getResources().getColor(R.color.white));
                    dialogLayoutBinding.chat.setTextColor(getResources().getColor(R.color.dark_grey));
                    dialogLayoutBinding.call.setTextColor(getResources().getColor(R.color.text_color));
                });
                dialogLayoutBinding.call.setOnClickListener(view -> {
                    dialogLayoutBinding.call.setShapeType(1);
                    dialogLayoutBinding.chat.setShapeType(0);
                    dialogLayoutBinding.callLayout.setVisibility(View.VISIBLE);
                    dialogLayoutBinding.chatLayout.setVisibility(View.GONE);
                    dialogLayoutBinding.call.setBackgroundColor(getResources().getColor(R.color.base_blue));
                    dialogLayoutBinding.chat.setBackgroundColor(getResources().getColor(R.color.white));
                    dialogLayoutBinding.call.setTextColor(getResources().getColor(R.color.dark_grey));
                    dialogLayoutBinding.chat.setTextColor(getResources().getColor(R.color.text_color));
                });

                NextOnlineTimeAdapter dialogAdapter = new NextOnlineTimeAdapter(getApplicationContext());
                dialogLayoutBinding.chatTimeList.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
                dialogLayoutBinding.chatTimeList.setAdapter(dialogAdapter);
                dialogLayoutBinding.callTimeList.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
                dialogLayoutBinding.callTimeList.setAdapter(dialogAdapter);
                final Calendar newCalendar = Calendar.getInstance();
                final DatePickerDialog chatDateDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM,EEE");
                    dialogLayoutBinding.chatDate.setText(simpleDateFormat.format(newDate.getTime()));
                    chatDate = new SimpleDateFormat("yyyy-MM-dd").format(newDate.getTime());
//                    dialogLayoutBinding.startDate.setText(simpleDateFormat.format(newDate.getTime()));
//                    sdate = simpleDateFormat.format(newDate.getTime());
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                dialogLayoutBinding.chatDateLayout.setOnClickListener(view -> chatDateDialog.show());
                final DatePickerDialog callDateDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM,EEE");
                    dialogLayoutBinding.callDate.setText(simpleDateFormat.format(newDate.getTime()));
                    callDate = new SimpleDateFormat("yyyy-MM-dd").format(newDate.getTime());

//                    dialogLayoutBinding.startDate.setText(simpleDateFormat.format(newDate.getTime()));
//                    sdate = simpleDateFormat.format(newDate.getTime());
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                dialogLayoutBinding.callDateLayout.setOnClickListener(view -> {
                    callDateDialog.show();
                });

                dialogLayoutBinding.chatTimeLayout.setOnClickListener(view -> {
                    final Calendar calendar=Calendar.getInstance();

                    TimePickerDialog.OnTimeSetListener timeSetListener= (view1, hourOfDay, minute) -> {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("hh:mm a");
                        chatTime = new SimpleDateFormat("kk:mm:ss").format(calendar.getTime());
                        dialogLayoutBinding.chatTime.setText(simpleDateFormat.format(calendar.getTime()));
                    };

                    new TimePickerDialog(DashboardActivity.this,TimePickerDialog.THEME_HOLO_LIGHT,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
                });
                dialogLayoutBinding.callTimeLayout.setOnClickListener(view -> {
                    final Calendar calendar=Calendar.getInstance();

                    TimePickerDialog.OnTimeSetListener timeSetListener= (view1, hourOfDay, minute) -> {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("hh:mm a");
                        callTime = new SimpleDateFormat("kk:mm:ss").format(calendar.getTime());
                        dialogLayoutBinding.callTime.setText(simpleDateFormat.format(calendar.getTime()));
                    };

                    new TimePickerDialog(DashboardActivity.this,TimePickerDialog.THEME_HOLO_LIGHT,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
                });
                dialogLayoutBinding.setChatTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (chatDate == null){
                            chatDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                        }
                        if (chatTime == null){
                            chatTime = new SimpleDateFormat("kk:mm:ss").format(new Date());
                        }
                        SaveNextOnlineApi(chatDate+" "+chatTime,"chat");
                        dialogLayoutBinding.chatTimeView.setText(dialogLayoutBinding.chatTime.getText());
                        dialogLayoutBinding.chatDateView.setText(dialogLayoutBinding.chatDate.getText());
                    }
                });
                dialogLayoutBinding.setCallTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (callDate == null){
                            callDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                        }
                        if (callTime == null){
                            callTime = new SimpleDateFormat("kk:mm:ss").format(new Date());
                        }
                        SaveNextOnlineApi(callDate+" "+callTime,"call");
                        dialogLayoutBinding.callTimeView.setText(dialogLayoutBinding.callTime.getText());
                        dialogLayoutBinding.callDateView.setText(dialogLayoutBinding.callDate.getText());
                    }
                });
                return true;

            case R.id.notification:
                startActivity(new Intent(getApplicationContext(),NotificationActivity.class ));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    SaveNextOnlineTimeResponseModel saveNextOnlineTimeResponseModel;
    private void SaveNextOnlineApi(String time, String isFor) {
        SaveNextOnlineTimeRequestModel requestModel = new SaveNextOnlineTimeRequestModel();
        requestModel.setAstrologerId(SharedPreferenceManager.getUserData(getApplicationContext()).getAstrologerId());
        requestModel.setNextOnlineTime(time);
        requestModel.setIsFor(isFor);

        RetrofitClient.getApiClient().saveOnlineTime(requestModel).enqueue(new Callback<SaveNextOnlineTimeResponseModel>() {
            @Override
            public void onResponse(Call<SaveNextOnlineTimeResponseModel> call, Response<SaveNextOnlineTimeResponseModel> response) {
                try {
                    if (response.body() != null && response.isSuccessful()){
                        if (response.body().getCode().equals("200")){
                            Log.i("TAGG", "onResponse: "+response.body().getMessage());
                            getNextOnlineTime();
                            Toast.makeText(DashboardActivity.this,response.body().getResult().getNextOnlineTime()+response.body().getResult().getIsFor()+ response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            saveNextOnlineTimeResponseModel = response.body();
                        }else {
                            Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),AppConstants.TOAST_MESSAGES,Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    AllStaticMethods.saveException(e);
                }
            }

            @Override
            public void onFailure(Call<SaveNextOnlineTimeResponseModel> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getNextOnlineTime(){
        RetrofitClient.getApiClient().getOnlineTime(SharedPreferenceManager.getUserData(getApplicationContext()).getAstrologerId()).enqueue(new Callback<NextOnlineResponseModel>() {
            @Override
            public void onResponse(Call<NextOnlineResponseModel> call, Response<NextOnlineResponseModel> response) {
                try {
                    if (response.body() != null && response.isSuccessful()){
                        if (response.body().getCode().equals("200")){
                            StaticFields.nextOnlineTime = response.body().getResult();
                            Log.i(TAG, "onResponse: "+response.body().getMessage());
                        }else {
                            Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),AppConstants.TOAST_MESSAGES,Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    AllStaticMethods.saveException(e);
                }
            }

            @Override
            public void onFailure(Call<NextOnlineResponseModel> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce){
            super.onBackPressed();
            finishAffinity();
            finish();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}