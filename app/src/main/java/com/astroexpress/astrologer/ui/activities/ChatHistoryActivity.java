package com.astroexpress.astrologer.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.adapter.ChatHistoryAdapter;
import com.astroexpress.astrologer.databinding.ActivityChatHistoryBinding;
import com.astroexpress.astrologer.databinding.BlockUserDialogBinding;
import com.astroexpress.astrologer.databinding.RatingReviewDialogBinding;
import com.astroexpress.astrologer.models.request.BlockUserRequestModel;
import com.astroexpress.astrologer.models.request.UserRatingReviewRequestModel;
import com.astroexpress.astrologer.models.response.BlockUserResponseModel;
import com.astroexpress.astrologer.models.response.UserRatingReviewResponseModel;
import com.astroexpress.astrologer.models.response.WalletsModel;
import com.astroexpress.astrologer.network.RetrofitClient;
import com.astroexpress.astrologer.utils.AllStaticMethods;
import com.astroexpress.astrologer.utils.AppConstants;
import com.astroexpress.astrologer.utils.NetworkStats;
import com.astroexpress.astrologer.utils.StaticFields;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatHistoryActivity extends AppCompatActivity {

    ActivityChatHistoryBinding binding;

    ChatHistoryAdapter chatHistoryAdapter;
    NetworkStats networkStats = new NetworkStats();
    Dialog dialog;
    Dialog blockDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar));
        binding = ActivityChatHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.left_back);
        binding.toolbar.setNavigationOnClickListener(view -> onBackPressed());

        apiCallForChatHistory();
    }

    private void apiCallForChatHistory() {
        RetrofitClient.getApiClient().wallet(StaticFields.astrologerData.getAstrologerId()).enqueue(new Callback<WalletsModel>() {
            @Override
            public void onResponse(Call<WalletsModel> call, Response<WalletsModel> response) {
                try {
                    if (response.isSuccessful() && response.body() !=null){
                        if (response.body().getCode().equals("200")){
                            binding.progress.setVisibility(View.GONE);
                            binding.statusMsg.setVisibility(View.GONE);
                            chatHistoryAdapter = new ChatHistoryAdapter(getApplicationContext(),response.body().getResult());
                            binding.chatHistoryList.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));

                            chatHistoryAdapter.setOnItemClick(new ChatHistoryAdapter.OnItemClick() {
                                @Override
                                public void getPosition(int position, String userId) {
                                    openRatingDialog(userId);
                                }
                            });
                            binding.chatHistoryList.setAdapter(chatHistoryAdapter);

                        }else {
                            binding.progress.setVisibility(View.GONE);
                            binding.statusMsg.setVisibility(View.VISIBLE);
                        }
                    }
                }catch (Exception e){
                    AllStaticMethods.saveException(e);
                }
            }

            @Override
            public void onFailure(Call<WalletsModel> call, Throwable t) {
                Toast.makeText(ChatHistoryActivity.this, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openRatingDialog(String userId) {
        dialog = new Dialog(this);
        RatingReviewDialogBinding ratingReviewDialogBinding = RatingReviewDialogBinding.inflate(getLayoutInflater());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(ratingReviewDialogBinding.getRoot());
        dialog.setCancelable(true);
        ratingReviewDialogBinding.block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBlockDialog(userId,StaticFields.astrologerData.getAstrologerId());
            }
        });
        ratingReviewDialogBinding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ratingReviewDialogBinding.ratingBar.getNumStars() != 0){
                    if (!ratingReviewDialogBinding.review.getText().toString().isEmpty()){
                        callSaveUserRatingApi(userId, String.valueOf(ratingReviewDialogBinding.ratingBar.getRating()),ratingReviewDialogBinding.review.getText().toString());
                    }else {
                        Toast.makeText(ChatHistoryActivity.this, "Review Cannot be Empty", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(ChatHistoryActivity.this, "Set rating before Submit", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    private void openBlockDialog(String userId, String astrologerId) {
        blockDialog = new Dialog(this);
        BlockUserDialogBinding blockUserDialogBinding = BlockUserDialogBinding.inflate(getLayoutInflater());
        blockDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        blockDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        blockDialog.setContentView(blockUserDialogBinding.getRoot());
        blockDialog.setCancelable(true);
        blockUserDialogBinding.submitBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!blockUserDialogBinding.review.getText().toString().isEmpty()){
                    callBlockUserApi(userId,blockUserDialogBinding.review.getText().toString());
                }else {
                    Toast.makeText(ChatHistoryActivity.this, "Cannot Block without Review", Toast.LENGTH_SHORT).show();
                }
            }
        });
        blockDialog.show();
    }

    private void callBlockUserApi(String userId,String reason) {
        BlockUserRequestModel blockUserRequestModel = new BlockUserRequestModel();
        blockUserRequestModel.setAstrologerId(StaticFields.astrologerData.getAstrologerId());
        blockUserRequestModel.setUserId(userId);
        blockUserRequestModel.setReason(reason);
        RetrofitClient.getApiClient().blockUser(blockUserRequestModel).enqueue(new Callback<BlockUserResponseModel>() {
            @Override
            public void onResponse(Call<BlockUserResponseModel> call, Response<BlockUserResponseModel> response) {
                try {
                    if (response.body() != null && response.isSuccessful()){
                        if (response.body().getCode().equals("200")){
                            blockDialog.dismiss();
                            dialog.dismiss();
                            Toast.makeText(ChatHistoryActivity.this, "Successfully Blocked", Toast.LENGTH_SHORT).show();
                        }else if (response.body().getCode().equals("1062")){
                            blockDialog.dismiss();
                            dialog.dismiss();
                            Toast.makeText(ChatHistoryActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ChatHistoryActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),AppConstants.TOAST_MESSAGES,Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    AllStaticMethods.saveException(e);
                }
            }

            @Override
            public void onFailure(Call<BlockUserResponseModel> call, Throwable t) {
                Toast.makeText(ChatHistoryActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callSaveUserRatingApi(String userId,String numStars,String review) {
        UserRatingReviewRequestModel requestModel = new UserRatingReviewRequestModel();
        requestModel.setAstrologerId(StaticFields.astrologerData.getAstrologerId());
        requestModel.setUserId(userId);
        requestModel.setRatingCount(numStars);
        requestModel.setReview(review);
        RetrofitClient.getApiClient().saveRatingReview(requestModel).enqueue(new Callback<UserRatingReviewResponseModel>() {
            @Override
            public void onResponse(Call<UserRatingReviewResponseModel> call, Response<UserRatingReviewResponseModel> response) {
                try {
                    if (response.body() != null && response.isSuccessful()){
                        if (response.body().getCode().equals("200")){
                            Dialog successDialog = new Dialog(ChatHistoryActivity.this);
                            successDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            successDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            successDialog.setContentView(R.layout.dialog_success_layout);
                            successDialog.setCancelable(true);
                            successDialog.findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    successDialog.dismiss();
                                }
                            });
                            successDialog.show();
                            Toast.makeText(ChatHistoryActivity.this, "Successfully Submitted", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else {
                            Toast.makeText(ChatHistoryActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),AppConstants.TOAST_MESSAGES,Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    AllStaticMethods.saveException(e);
                }
            }

            @Override
            public void onFailure(Call<UserRatingReviewResponseModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
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