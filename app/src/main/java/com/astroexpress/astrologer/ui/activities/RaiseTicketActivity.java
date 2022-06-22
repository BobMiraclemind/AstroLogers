package com.astroexpress.astrologer.ui.activities;

import android.app.Dialog;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.databinding.ActivityRaiseTicketBinding;
import com.astroexpress.astrologer.databinding.RaiseTicketDialogLayoutBinding;
import com.astroexpress.astrologer.models.request.RaiseTicketRequestModel;
import com.astroexpress.astrologer.models.response.RaiseTicketResponseModel;
import com.astroexpress.astrologer.network.RetrofitClient;
import com.astroexpress.astrologer.utils.AllStaticMethods;
import com.astroexpress.astrologer.utils.AppConstants;
import com.astroexpress.astrologer.utils.NetworkStats;
import com.astroexpress.astrologer.utils.StaticFields;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RaiseTicketActivity extends AppCompatActivity {

    ActivityRaiseTicketBinding binding;
    NetworkStats networkStats = new NetworkStats();
    RaiseTicketDialogLayoutBinding dialogLayoutBinding;
    RaiseTicketRequestModel raiseTicketRequestModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar));
        binding = ActivityRaiseTicketBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialogLayoutBinding = RaiseTicketDialogLayoutBinding.inflate(getLayoutInflater());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.left_back);
        binding.toolbar.setNavigationOnClickListener(view -> onBackPressed());

        binding.submit.setOnClickListener(view -> {
            binding.progress.setVisibility(View.VISIBLE);
            if (binding.subject.getText().toString().isEmpty()){
                binding.progress.setVisibility(View.GONE);
                binding.subject.setError("Empty");
            }else if (binding.description.getText().toString().isEmpty()){
                binding.progress.setVisibility(View.GONE);
                binding.description.setError("Empty");
            }else {
                callSubmitQuery();
            }
        });

        /*binding.submit.setOnClickListener(view -> {
            binding.progress.setVisibility(View.VISIBLE);
            if (binding.subject.getText().toString().isEmpty()){
                binding.subject.setError("Empty");
                binding.progress.setVisibility(View.INVISIBLE);
            }else if (binding.description.getText().toString().isEmpty()){
                binding.description.setError("Empty");
                binding.progress.setVisibility(View.INVISIBLE);
            }else {

                RetrofitClient.getApiClient().submitQuery(raiseTicketRequestModel).enqueue(new Callback<RaiseTicketResponseModel>() {
                    @Override
                    public void onResponse(Call<RaiseTicketResponseModel> call, Response<RaiseTicketResponseModel> response) {
                        try {
                            if (response.isSuccessful() && response.body() != null){
                                if (response.body().getCode().equals("200")){

                                }else {

                                }
                            }else {

                            }
                        }catch (Exception e){
                            AllStaticMethods.saveException(e);
                        }
                    }

                    @Override
                    public void onFailure(Call<RaiseTicketResponseModel> call, Throwable t) {

                    }
                });
//                callSubmitQuery(loginId,fullname,binding.subject.getText().toString(),binding.description.getText().toString(),email,mobile);
//                callSubmitQuery("0","",binding.subject.getText().toString(),binding.description.getText().toString(),"","");
            }
        });*/
    }

    private void callSubmitQuery() {

        RaiseTicketRequestModel raiseTicketRequestModel = new RaiseTicketRequestModel(StaticFields.astrologerData.getAstrologerId(),
                StaticFields.astrologerData.getFirstname()+" "+StaticFields.astrologerData.getLastname(),
                binding.subject.getText().toString(),
                binding.description.getText().toString(),
                StaticFields.astrologerData.getEmail(),
                StaticFields.astrologerData.getMobile());

        /*Log.i("RaiseTicketRequest", "callSubmitQuery: "+raiseTicketRequestModel.getLoginId()+
                raiseTicketRequestModel.getFullName()+
                raiseTicketRequestModel.getTitle()+
                raiseTicketRequestModel.getDescription()+
                raiseTicketRequestModel.getEmail()+
                raiseTicketRequestModel.getMobile());*/

        RetrofitClient.getApiClient().submitQuery(raiseTicketRequestModel).enqueue(new Callback<RaiseTicketResponseModel>() {
            @Override
            public void onResponse(Call<RaiseTicketResponseModel> call, Response<RaiseTicketResponseModel> response) {
                try {
                    if (response.isSuccessful() && response.body() != null){
                        if (response.body().getCode().equals("200")){
                            Toast.makeText(getApplicationContext(),response.body().getResult().getTokenId(),Toast.LENGTH_SHORT).show();
                            binding.progress.setVisibility(View.GONE);
                            successDialog(response.body().getResult().getTokenId(),response.body().getResult().getHelpTokenId());
                            Dialog dialog = new Dialog(getApplicationContext());
                            dialog.setContentView(R.layout.raise_ticket_dialog_layout);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.setCancelable(true);
                            TextView textView = dialog.findViewById(R.id.token_id);
                            textView.setText(response.body().getResult().getTokenId());
//                            TextView helpTokenTv = dialog.findViewById(R.id.help_token_id);
//                            helpTokenTv.setText(response.body().getResult().getHelpTokenId());
                            dialog.show();
                            dialog.findViewById(R.id.btn_ok).setOnClickListener(view -> {
                                binding.subject.setText("");
                                binding.description.setText("");
                                dialog.dismiss();
                            });
                        }else {
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    AllStaticMethods.saveException(e);
                }
            }

            @Override
            public void onFailure(Call<RaiseTicketResponseModel> call, Throwable t) {

            }
        });
    }

    private void successDialog(String tokenId,int helpId) {

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