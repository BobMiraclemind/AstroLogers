package com.astroexpress.astrologer.ui.activities;

import android.app.Dialog;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.adapter.ProfileGalleryAdapter;
import com.astroexpress.astrologer.adapter.ProfileImgPreviewAdapter;
import com.astroexpress.astrologer.adapter.UserReviewsAdapter;
import com.astroexpress.astrologer.databinding.ActivityAstroProfileBinding;
import com.astroexpress.astrologer.models.ProfileGalleryModel;
import com.astroexpress.astrologer.models.response.AstrologerContentModel;
import com.astroexpress.astrologer.network.RetrofitClient;
import com.astroexpress.astrologer.utils.AllStaticMethods;
import com.astroexpress.astrologer.utils.NetworkStats;
import com.astroexpress.astrologer.utils.StaticFields;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AstroProfileActivity extends AppCompatActivity {

    ActivityAstroProfileBinding binding;

    NetworkStats networkStats = new NetworkStats();

    ProfileGalleryAdapter profileGalleryAdapter;
    ProfileImgPreviewAdapter profileImgPreviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar));
        binding = ActivityAstroProfileBinding.inflate(getLayoutInflater());
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

        binding.gotoAbout.setOnClickListener(view -> binding.scrollView.smoothScrollTo(0,binding.cardView.getTop()));
        binding.gotoRatings.setOnClickListener(view -> binding.scrollView.smoothScrollTo(0,binding.cardView1.getTop()));
        binding.gotoReviews.setOnClickListener(view -> binding.scrollView.smoothScrollTo(0,binding.cardView2.getTop()));
        binding.setSchedule.setOnClickListener(view -> Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_LONG).show());

        UserReviewsAdapter userReviewsAdapter = new UserReviewsAdapter(getApplicationContext());
        binding.reviews.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.reviews.setAdapter(userReviewsAdapter);

        callAstrologerContentApi();

        setDataOnFields();

    }

    private void callAstrologerContentApi() {
        RetrofitClient.getApiClient().getAstrologerContent(StaticFields.astrologerData.getAstrologerId()).enqueue(new Callback<AstrologerContentModel>() {
            @Override
            public void onResponse(Call<AstrologerContentModel> call, Response<AstrologerContentModel> response) {
                try {
                    if (response.body()!=null && response.isSuccessful()){
                        if (response.body().getCode().equals("200")){
                            profileGalleryAdapter = new ProfileGalleryAdapter(getApplicationContext(), response.body().getResult());
                            binding.galleryView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                            profileGalleryAdapter.setOnItemClick(new ProfileGalleryAdapter.OnItemClick() {
                                @Override
                                public void getPosition(int position) {
                                    final Dialog dialog = new Dialog(AstroProfileActivity.this);
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog.setContentView(R.layout.image_preview_profile_layout);
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    ViewPager viewPager = dialog.findViewById(R.id.viewpager_preview);
                                    profileImgPreviewAdapter = new ProfileImgPreviewAdapter(getApplicationContext(),response.body().getResult());
                                    viewPager.setAdapter(profileImgPreviewAdapter);
                                    viewPager.setCurrentItem(position);
//                                    viewPager.setClipToPadding(false);
//                                    viewPager.setPadding(10,0,10,0);
                                    dialog.show();
                                }
                            });
                            binding.galleryView.setAdapter(profileGalleryAdapter);
                        }
                    }
                }catch (Exception e){
                    AllStaticMethods.saveException(e);
                }
            }

            @Override
            public void onFailure(Call<AstrologerContentModel> call, Throwable t) {

            }
        });
    }

    private void setDataOnFields() {
        if (StaticFields.astrologerData != null){
            binding.firstname.setText(StaticFields.astrologerData.getFirstname()==null?"":StaticFields.astrologerData.getFirstname());
            binding.lastname.setText(StaticFields.astrologerData.getLastname()==null?"":StaticFields.astrologerData.getLastname());
            Glide.with(getApplicationContext())
                    .load(StaticFields.astrologerData.getProfileImgUrl())
                    .centerCrop()
                    .placeholder(R.drawable.place_holder)
                    .into(binding.profileImg);
            binding.languages.setText(StaticFields.astrologerData.getLanguage()==null?"":StaticFields.astrologerData.getLanguage());
            binding.skills.setText(StaticFields.astrologerData.getSpeciality()==null?"":StaticFields.astrologerData.getSpeciality());
            binding.charge.setText(StaticFields.astrologerData.getChargePerMin()==null?"":StaticFields.astrologerData.getChargePerMin());
            binding.totalCallMin.setText(StaticFields.astrologerData.getTotalCallMinutes()==null?"":StaticFields.astrologerData.getTotalCallMinutes());
            binding.totalChatMin.setText(StaticFields.astrologerData.getTotalChatMinutes()==null?"":StaticFields.astrologerData.getTotalChatMinutes());
            binding.aboutContent.setText(StaticFields.astrologerData.getAboutUs()==null?"":StaticFields.astrologerData.getAboutUs());
            binding.rating.setText(StaticFields.astrologerData.getRating()==null?"":StaticFields.astrologerData.getRating());
            binding.avgRating.setRating(Float.parseFloat(StaticFields.astrologerData.getRating()==null?"":StaticFields.astrologerData.getRating()));
            binding.experience.setText(StaticFields.astrologerData.getExperience()==null?"":StaticFields.astrologerData.getExperience()+" Years");
        }
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