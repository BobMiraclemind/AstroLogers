package com.astroexpress.astrologer.ui.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.adapter.WalletAdapter;
import com.astroexpress.astrologer.adapter.WalletEarningAdapter;
import com.astroexpress.astrologer.databinding.ActivityWalletBinding;
import com.astroexpress.astrologer.databinding.WalletCustomDialogLayoutBinding;
import com.astroexpress.astrologer.databinding.WalletDetailPopupLayoutBinding;
import com.astroexpress.astrologer.models.response.EarningResponseModel;
import com.astroexpress.astrologer.models.response.LastMonthEarningModel;
import com.astroexpress.astrologer.models.response.WalletsModel;
import com.astroexpress.astrologer.network.RetrofitClient;
import com.astroexpress.astrologer.utils.AllStaticMethods;
import com.astroexpress.astrologer.utils.AppConstants;
import com.astroexpress.astrologer.utils.NetworkStats;
import com.astroexpress.astrologer.utils.StaticFields;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ActivityWalletBinding binding;
    private static final String TAG = "walletactivity";
    WalletAdapter walletAdapter;
    WalletEarningAdapter earningAdapter;
    Dialog dialog;
    String sdate,edate;
    NetworkStats networkStats = new NetworkStats();
    WalletCustomDialogLayoutBinding dialogLayoutBinding;
    String[] setIntervals = {"Select intervals","See All", "Today", "Yesterday", "This Week", "Last Week", "This Month", "Last Month"};
    WalletDetailPopupLayoutBinding popupLayoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar));
        binding = ActivityWalletBinding.inflate(getLayoutInflater());
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

        binding.firstname.setText(StaticFields.astrologerData.getFirstname());
        binding.lastname.setText(StaticFields.astrologerData.getLastname());
        binding.progress.setVisibility(View.VISIBLE);
        getDataForWallet();

        dialog = new Dialog(this);
        dialogLayoutBinding = WalletCustomDialogLayoutBinding.inflate(getLayoutInflater());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogLayoutBinding.getRoot());
        dialog.setCancelable(true);
        binding.setInterval.setOnClickListener(view -> dialog.show());

        dialogLayoutBinding.btnCancel.setOnClickListener(view -> dialog.dismiss());

        final Calendar newCalendar = Calendar.getInstance();
        final DatePickerDialog  StartTime = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dialogLayoutBinding.startDate.setText(simpleDateFormat.format(newDate.getTime()));
            sdate = simpleDateFormat.format(newDate.getTime());
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        dialogLayoutBinding.startDate.setOnClickListener(view -> StartTime.show());

        final DatePickerDialog  EndTime = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dialogLayoutBinding.endDate.setText(simpleDateFormat.format(newDate.getTime()));
            edate = simpleDateFormat.format(newDate.getTime());
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        dialogLayoutBinding.endDate.setOnClickListener(view -> EndTime.show());
        dialogLayoutBinding.btnGo.setOnClickListener(view -> {
            if (!dialogLayoutBinding.startDate.getText().toString().isEmpty() && !dialogLayoutBinding.endDate.getText().toString().isEmpty()){
                getEarningData(StaticFields.astrologerData.getAstrologerId(),sdate,edate);
//                    dialog.dismiss();
            }else {
                Toast.makeText(WalletActivity.this, "Please select start and end date", Toast.LENGTH_SHORT).show();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(WalletActivity.this, android.R.layout.simple_spinner_item, setIntervals);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialogLayoutBinding.setInterval.setAdapter(adapter);

        dialogLayoutBinding.setInterval.setOnItemSelectedListener(this);
        getMonthEarning();

        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        popupLayoutBinding = WalletDetailPopupLayoutBinding.inflate(inflater);
        PopupWindow popupWindow = new PopupWindow(popupLayoutBinding.getRoot(), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        binding.seeDetail.setOnClickListener(view -> {
            if (popupWindow.isShowing()){
                popupWindow.dismiss();
            }else {
                popupWindow.setFocusable(true);
                popupWindow.showAsDropDown(binding.seeDetail,0,0);
            }
        });
    }

    private void getMonthEarning() {
        RetrofitClient.getApiClient().lastMonthEarningReport(StaticFields.astrologerData.getAstrologerId()).enqueue(new Callback<LastMonthEarningModel>() {
            @Override
            public void onResponse(Call<LastMonthEarningModel> call, Response<LastMonthEarningModel> response) {
                try {
                    if (response.body() != null && response.isSuccessful()){
                        if (response.body().getCode().equals("200")){
                            LastMonthEarningModel.Result earningModel = response.body().getResult();
                            binding.earnedPrice.setText(earningModel.getTotalEarning());
                            binding.payAmount.setText(earningModel.getFinalEarning());
                            popupLayoutBinding.earnedAmount.setText(earningModel.getTotalEarning());
                            popupLayoutBinding.pgCharges.setText(earningModel.getPgCharges());
                            popupLayoutBinding.pgPercent.setText(earningModel.getPgChargesPercent());
                            popupLayoutBinding.tdsCharge.setText(earningModel.getTds());
                            popupLayoutBinding.tdsPercent.setText(earningModel.getTdsPercent());
                            popupLayoutBinding.payAmount.setText(earningModel.getFinalEarning());
                        }
                    }
                }catch (Exception e){
                    AllStaticMethods.saveException(e);
                }
            }

            @Override
            public void onFailure(Call<LastMonthEarningModel> call, Throwable t) {

            }
        });
    }

    private void getEarningData(String astrologerId,String startDate,String endDate) {
        binding.progress.setVisibility(View.VISIBLE);
        RetrofitClient.getApiClient().getEarnings(astrologerId, startDate, endDate).enqueue(new Callback<EarningResponseModel>() {
            @Override
            public void onResponse(Call<EarningResponseModel> call, Response<EarningResponseModel> response) {
                try {
                    if (response.body() != null && response.isSuccessful()){
                        if (response.body().getCode().equals("200")){
                            binding.progress.setVisibility(View.GONE);
                            binding.statusMsg.setVisibility(View.GONE);
                            dialog.dismiss();
                            earningAdapter = new WalletEarningAdapter(getApplicationContext(),response.body().getResult());
                            binding.transactionView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
                            binding.transactionView.setAdapter(earningAdapter);
                            earningAdapter.notifyDataSetChanged();
                        }else {
                            binding.progress.setVisibility(View.GONE);
                            binding.statusMsg.setVisibility(View.VISIBLE);
                            binding.statusMsg.setText(response.body().getMessage()==null?"":response.body().getMessage());
                        }
                    }else {
                        binding.progress.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    AllStaticMethods.saveException(e);
                }
            }

            @Override
            public void onFailure(Call<EarningResponseModel> call, Throwable t) {

            }
        });
    }

    private void getDataForWallet() {
        RetrofitClient.getApiClient().wallet(StaticFields.astrologerData.getAstrologerId()).enqueue(new Callback<WalletsModel>() {
            @Override
            public void onResponse(Call<WalletsModel> call, Response<WalletsModel> response) {
                try {
                    if (response.body() != null && response.isSuccessful()){
                        if (response.body().getCode().equals("200")){
                            binding.statusMsg.setVisibility(View.GONE);
                            walletAdapter = new WalletAdapter(getApplicationContext(),response.body().getResult());
                            binding.transactionView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
                            binding.transactionView.setAdapter(walletAdapter);
                            binding.progress.setVisibility(View.GONE);
                        }else {
                            binding.progress.setVisibility(View.GONE);
                            binding.statusMsg.setVisibility(View.VISIBLE);
                            binding.statusMsg.setText(response.body().getMessage());
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), AppConstants.TOAST_MESSAGES,Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    AllStaticMethods.saveException(e);
                }
            }

            @Override
            public void onFailure(Call<WalletsModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        switch (position){
            case 1://See all
                binding.setInterval.setText("Showing All");
                getDataForWallet();
                dialog.dismiss();
                break;
            case 2://today
                binding.setInterval.setText("Today");
                getEarningData(StaticFields.astrologerData.getAstrologerId(),simpleDateFormat.format(date),simpleDateFormat.format(date));
                Log.i(TAG, "onItemSelected: "   +simpleDateFormat.format(date)+" "+simpleDateFormat.format(date));
                dialog.dismiss();
                break;
            case 3://yesterday
                binding.setInterval.setText("Yesterday");
                dialog.dismiss();
                calendar.add(Calendar.DAY_OF_YEAR,-1);
                Date oneDate = calendar.getTime();
                Log.i(TAG, "onItemSelected: "   +simpleDateFormat.format(oneDate)+" "+simpleDateFormat.format(date));
                getEarningData(StaticFields.astrologerData.getAstrologerId(),simpleDateFormat.format(oneDate),simpleDateFormat.format(oneDate));
                break;
            case 4://this week
                binding.setInterval.setText("This week");
                calendar.set(Calendar.DAY_OF_WEEK,calendar.getFirstDayOfWeek());
                Date startWeek = calendar.getTime();
                getEarningData(StaticFields.astrologerData.getAstrologerId(),simpleDateFormat.format(startWeek),simpleDateFormat.format(date));
                dialog.dismiss();
                break;
            case 5://last week
                binding.setInterval.setText("Last Week");
                int i = calendar.get(Calendar.DAY_OF_WEEK) - calendar.getFirstDayOfWeek();
                calendar.add(Calendar.DATE,-i - 7);
                Date start = calendar.getTime();
                calendar.add(Calendar.DATE, 6);
                Date end = calendar.getTime();
                getEarningData(StaticFields.astrologerData.getAstrologerId(), simpleDateFormat.format(start),simpleDateFormat.format(end));
                dialog.dismiss();
                break;
            case 6://this month
                binding.setInterval.setText("This Month");
                calendar.set(Calendar.DAY_OF_MONTH,1);
                Date startMonth = calendar.getTime();
                getEarningData(StaticFields.astrologerData.getAstrologerId(),simpleDateFormat.format(startMonth),simpleDateFormat.format(date));
                dialog.dismiss();
                break;
            case 7://last month
                binding.setInterval.setText("Last Month");
                calendar.set(Calendar.DATE,1);
                calendar.add(Calendar.DAY_OF_MONTH,-1);
                Date lastDate = calendar.getTime();
                calendar.set(Calendar.DATE,1);
                Date firstDate = calendar.getTime();
                getEarningData(StaticFields.astrologerData.getAstrologerId(),simpleDateFormat.format(firstDate),simpleDateFormat.format(lastDate));
                dialog.dismiss();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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