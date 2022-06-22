package com.astroexpress.astrologer.utils;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

import com.astroexpress.astrologer.R;

public class NetworkStats extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!CheckNetConnection.isConnectedToInternet(context)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View layout_dialog = LayoutInflater.from(context).inflate(R.layout.network_stats_layout,null);
            builder.setView(layout_dialog);

            AppCompatButton retry = layout_dialog.findViewById(R.id.retry);

            AlertDialog alertDialog = builder.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.show();
            alertDialog.setCancelable(false);
            alertDialog.getWindow().setGravity(Gravity.CENTER);
            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    onReceive(context,intent);
                }
            });
        }
    }
}