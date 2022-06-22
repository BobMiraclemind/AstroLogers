package com.astroexpress.astrologer.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.adapter.SuggestRemedyAttachmentAdapter;
import com.astroexpress.astrologer.databinding.ActivitySuggestRemedyBinding;
import com.astroexpress.astrologer.models.response.SaveRemedyResponseModel;
import com.astroexpress.astrologer.models.response.SuggestRemedyCategoryModel;
import com.astroexpress.astrologer.network.RetrofitClient;
import com.astroexpress.astrologer.utils.AllStaticMethods;
import com.astroexpress.astrologer.utils.AppConstants;
import com.astroexpress.astrologer.utils.PathUtil;
import com.astroexpress.astrologer.utils.StaticFields;
import com.astroexpress.astrologer.utils.Utilities;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuggestRemedyActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getName();
    ActivitySuggestRemedyBinding binding;
    final private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    private final int IMAGE_REQUEST_CODE = 101101;
    private final int PERMISSION_REQUEST = 102201;

    private List<SuggestRemedyCategoryModel.Result> list;
    final private List<Uri> imageUriList = new ArrayList<>();
    String categoryId;
    String userId;
    SuggestRemedyAttachmentAdapter suggestRemedyAttachmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar));
        binding = ActivitySuggestRemedyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.left_back);
        binding.toolbar.setNavigationOnClickListener(view -> onBackPressed());

        userId = getIntent().getStringExtra("userId");
        binding.freeBtn.setOnClickListener(view -> {
            binding.freeBtn.setShapeType(1);
            binding.paidButton.setShapeType(0);
            binding.paidRemedyContainer.setVisibility(View.GONE);
            binding.freeRemedyContainer.setVisibility(View.VISIBLE);
            binding.freeBtn.setBackgroundColor(getResources().getColor(R.color.base_blue));
            binding.freeBtn.setTextColor(getResources().getColor(R.color.dark_grey));
            binding.paidButton.setBackgroundColor(getResources().getColor(R.color.white));
            binding.paidButton.setTextColor(getResources().getColor(R.color.text_color));
        });
        binding.paidButton.setOnClickListener(view -> {
            binding.freeBtn.setShapeType(0);
            binding.paidButton.setShapeType(1);
            binding.freeRemedyContainer.setVisibility(View.GONE);
            binding.paidRemedyContainer.setVisibility(View.VISIBLE);
            binding.paidButton.setBackgroundColor(getResources().getColor(R.color.base_blue));
            binding.paidButton.setTextColor(getResources().getColor(R.color.dark_grey));
            binding.freeBtn.setBackgroundColor(getResources().getColor(R.color.white));
            binding.freeBtn.setTextColor(getResources().getColor(R.color.text_color));
        });
        /*binding.astroMarket.setOnClickListener(view -> {
            binding.astroMarket.setShapeType(1);
            binding.createYourOwn.setShapeType(0);
            binding.comingSoon.setVisibility(View.VISIBLE);
            binding.linearLayout6.setVisibility(View.GONE);
            binding.astroMarket.setBackgroundColor(getResources().getColor(R.color.base_blue));
            binding.astroMarket.setTextColor(getResources().getColor(R.color.dark_grey));
            binding.createYourOwn.setBackgroundColor(getResources().getColor(R.color.white));
            binding.createYourOwn.setTextColor(getResources().getColor(R.color.text_color));
        });*/
        /*binding.createYourOwn.setOnClickListener(view -> {
            binding.astroMarket.setShapeType(0);
            binding.createYourOwn.setShapeType(1);
            binding.comingSoon.setVisibility(View.GONE);
            binding.linearLayout6.setVisibility(View.VISIBLE);
            binding.createYourOwn.setBackgroundColor(getResources().getColor(R.color.base_blue));
            binding.createYourOwn.setTextColor(getResources().getColor(R.color.dark_grey));
            binding.astroMarket.setBackgroundColor(getResources().getColor(R.color.white));
            binding.astroMarket.setTextColor(getResources().getColor(R.color.text_color));
        });*/
        binding.freeSuggestBtn.setOnClickListener(view -> {
            if (binding.freeRemedyDescription.getText().toString().isEmpty()){
                binding.freeRemedyDescription.setError("Empty");
                return;
            }else {
                onBackPressed();
            }
        });
        binding.paidSuggestBtn.setOnClickListener(view -> {
            if (binding.productName.getText().toString().isEmpty()){
                binding.productName.setError("Empty");
                return;
            }else if (binding.productPrice.getText().toString().isEmpty()){
                binding.productPrice.setError("Empty");
                return;
            }else if (binding.paidRemedyDescription.getText().toString().isEmpty()){
                binding.paidRemedyDescription.setError("Empty");
                return;
            }else {
                onBackPressed();
            }
        });

        binding.addImage.setOnClickListener(view -> {

            if (AllStaticMethods.hasPermissions(getApplicationContext(),permissions)==true){
                pickImage();
            }else {
                ActivityCompat.requestPermissions(this,permissions,PERMISSION_REQUEST);
            }
        });

        list = new ArrayList<>();

        callSuggestRemedyCategory();

        binding.selectCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SuggestRemedyCategoryModel.Result result= (SuggestRemedyCategoryModel.Result)adapterView.getSelectedItem();
                getRemedyId(result);
                Toast.makeText(getApplicationContext(),result.getRemedyCategoryId(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.paidSuggestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.productName.getText().toString().isEmpty()){
                    binding.productName.setError("Empty");
                }else if (binding.productPrice.getText().toString().isEmpty()){
                    binding.productPrice.setError("Empty");
                }else if(binding.paidRemedyDescription.getText().toString().isEmpty()){
                    binding.productPrice.setError("Empty");
                }else {
                    callSaveRemedyApi(binding.productPrice.getText().toString(),
                            binding.productName.getText().toString(),
                            categoryId,
                            binding.paidRemedyDescription.getText().toString());
                }
            }
        });

        pickImageRCViewInit();

    }

    private void pickImageRCViewInit() {
        suggestRemedyAttachmentAdapter = new SuggestRemedyAttachmentAdapter(getApplicationContext(),imageUriList);
        binding.remedyImgList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.remedyImgList.setAdapter(suggestRemedyAttachmentAdapter);
        suggestRemedyAttachmentAdapter.setOnItemClick(position -> {
            imageUriList.remove(position);
            suggestRemedyAttachmentAdapter.notifyItemRemoved(position);
            suggestRemedyAttachmentAdapter.notifyItemRangeChanged(position,imageUriList.size());
        });
    }

    private void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_REQUEST_CODE);
    }

    private void getRemedyId(SuggestRemedyCategoryModel.Result result) {
    }

    private void callSuggestRemedyCategory() {
        RetrofitClient.getApiClient().getSuggestRemedyCategory().enqueue(new Callback<SuggestRemedyCategoryModel>() {
            @Override
            public void onResponse(Call<SuggestRemedyCategoryModel> call, Response<SuggestRemedyCategoryModel> response) {
                try {
                    if (response.body() != null && response.isSuccessful()){
                        if (response.body().getCode().equals("200")){
                            ArrayAdapter<SuggestRemedyCategoryModel.Result> adapter = new ArrayAdapter<SuggestRemedyCategoryModel.Result>(getApplicationContext(), android.R.layout.simple_spinner_item,response.body().getResult());
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            binding.selectCategory.setAdapter(adapter);
                            binding.selectCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    ((TextView)adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.text_color));
                                    SuggestRemedyCategoryModel.Result category= (SuggestRemedyCategoryModel.Result) adapterView.getSelectedItem();
                                    getCategoryId(category);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }else {
                            Toast.makeText(SuggestRemedyActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(SuggestRemedyActivity.this, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    AllStaticMethods.saveException(e);
                }
            }

            @Override
            public void onFailure(Call<SuggestRemedyCategoryModel> call, Throwable t) {
                Toast.makeText(SuggestRemedyActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // do something
                pickImage();
            }
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == IMAGE_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    if (data.getClipData() != null) {
                        int count = data.getClipData().getItemCount();
                        for (int i = 0; i < count; i++) {
                            imageUriList.add(data.getClipData().getItemAt(i).getUri());
                        }
                        suggestRemedyAttachmentAdapter.notifyDataSetChanged();
                    }else if (data.getData() != null){
                        imageUriList.add(data.getData());
                        suggestRemedyAttachmentAdapter.notifyDataSetChanged();
                    }
                }
            }
        }catch (Exception e){
            AllStaticMethods.saveException(e);
            Log.i(TAG, "SuggestRemedyActivity "+"onActivityResult: "+e);
        }
        
    }

    private void callSaveRemedyApi(String price,String productName,String categoryID,String description){
        ArrayList<String> filePaths = new ArrayList<>();
        for (Uri singleUri:imageUriList){
            try {
                Log.i(TAG, "callSuggestRemedyApi:9 "+ PathUtil.getPath(getApplicationContext(),singleUri));
                filePaths.add(PathUtil.getPath(getApplicationContext(),singleUri));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("AstrologerId",StaticFields.astrologerData.getAstrologerId());
        builder.addFormDataPart("UserId",userId);
        builder.addFormDataPart("Price",binding.productPrice.getText().toString());
        builder.addFormDataPart("ProductName",binding.productName.getText().toString());
        builder.addFormDataPart("RemedyCategoryId",categoryId);
        builder.addFormDataPart("Description",binding.productName.getText().toString());

        for (int i = 0;i<filePaths.size();i++){
            File file = new File(filePaths.get(i));
            builder.addFormDataPart("Attachments[]",file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"),file));
        }

        RetrofitClient.getApiClient().saveRemedy(builder.build()).enqueue(new Callback<SaveRemedyResponseModel>() {
            @Override
            public void onResponse(Call<SaveRemedyResponseModel> call, Response<SaveRemedyResponseModel> response) {
                try {
                    if (response.isSuccessful() && response.body() != null){
                        if (response.body().getCode().equals("200")){
                            Log.i(TAG, "onResponse: "+response.body().getResult());
                            Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),response.message(),Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SaveRemedyResponseModel> call, Throwable t) {

            }
        });
    }

    private void getCategoryId(SuggestRemedyCategoryModel.Result category){
        categoryId = category.getRemedyCategoryId();
    }
}