package com.astroexpress.astrologer.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.astroexpress.astrologer.models.response.LoginsModel;
import com.astroexpress.astrologer.models.response.StatusModel;
import com.google.gson.Gson;

public class SharedPreferenceManager {

    public static String mySf = "mySf";
    public static String USER_DATA = "userdata";

    public static void setUserData(Context context, LoginsModel.Result result) {
        SharedPreferences sp = context.getSharedPreferences(mySf, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(USER_DATA, new Gson().toJson(result));
        editor.apply();
        StaticFields.astrologerData = result;
    }

    public static LoginsModel.Result getUserData(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences(mySf, MODE_PRIVATE);
        return new Gson().fromJson(sp.getString(USER_DATA, null), LoginsModel.Result.class);
    }


    public static void logout(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(mySf,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public static void saveStatus(Context context, StatusModel.Result result){
        SharedPreferences sp = context.getSharedPreferences("myStatus", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("myStatusData", new Gson().toJson(result));
        editor.apply();
        StaticFields.statusData = result;
    }

    public static StatusModel.Result getStatus(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences("myStatus", MODE_PRIVATE);
        return new Gson().fromJson(sp.getString("myStatusData",null), StatusModel.Result.class);
    }

    public static void removeStatus(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("myStatus",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
