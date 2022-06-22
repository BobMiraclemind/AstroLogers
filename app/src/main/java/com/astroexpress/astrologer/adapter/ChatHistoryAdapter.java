package com.astroexpress.astrologer.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.databinding.SingleChatListItemBinding;
import com.astroexpress.astrologer.models.response.WalletsModel;
import com.astroexpress.astrologer.ui.activities.SuggestRemedyActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChatHistoryAdapter extends RecyclerView.Adapter<ChatHistoryAdapter.ViewHolder> {
    private final Context context;
    private final List<WalletsModel.Result> walletsModel;
    OnItemClick onItemClick;
    long min;

    public ChatHistoryAdapter(Context context, List<WalletsModel.Result> walletsModel) {
        this.context = context;
        this.walletsModel = walletsModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_chat_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WalletsModel.Result walletItem = walletsModel.get(position);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String startTime = walletItem.getStartTime()==null?"00:00:00":walletItem.getStartTime();
        String endTime = walletItem.getEndTime()==null?"00:00:00":walletItem.getEndTime();
        try {
            Date d1 = simpleDateFormat.parse(startTime);
            Date d2 = simpleDateFormat.parse(endTime);
            Log.i("myTime", "onBindViewHolder: "+d1+d2);
            min = ((Math.abs(d2.getTime()- d1.getDate()))/(60*1000))%60;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (walletItem.getTransactionFor().equals("Chat")){
            holder.binding.orderId.setText(walletItem.getWalletTransactionId());
            holder.binding.startTime.setText(walletItem.getStartTime());
            holder.binding.endTime.setText(walletItem.getEndTime());
            holder.binding.firstname.setText(walletItem.getFirstname()==null?"":walletItem.getFirstname());
            holder.binding.lastname.setText(walletItem.getLastname()==null?"":walletItem.getLastname());
            holder.binding.userId.setText(walletItem.getUserId()==null?"":walletItem.getUserId());
            if (walletItem.getDebitedFrom().equals("Wallet")){
                holder.binding.rupee.setVisibility(View.VISIBLE);
                holder.binding.minute.setVisibility(View.GONE);
            }else {
                holder.binding.minute.setVisibility(View.VISIBLE);
                holder.binding.rupee.setVisibility(View.GONE);
            }
            holder.binding.creditAmount.setText(walletItem.getAmount());
            holder.binding.createdDateTime.setText(walletItem.getCreatedOn());
            holder.binding.startTime.setText(walletItem.getStartTime()==null?"00:00":walletItem.getStartTime());
            holder.binding.endTime.setText(walletItem.getEndTime()==null?"00:00":walletItem.getEndTime());
            holder.binding.duration.setText(String.valueOf(min)==null?"00:00":String.valueOf(min));
            holder.binding.suggestRemedy.setOnClickListener(view -> {
                Intent intent = new Intent(context, SuggestRemedyActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("orderId",walletItem.getWalletTransactionId());
                intent.putExtra("startTime",walletItem.getStartTime());
                intent.putExtra("endTime",walletItem.getEndTime());
                intent.putExtra("firstName",walletItem.getFirstname());
                intent.putExtra("lastName",walletItem.getLastname());
                intent.putExtra("userId",walletItem.getUserId());
                intent.putExtra("creditAmount",walletItem.getAmount());
                intent.putExtra("createdDateTime",walletItem.getCreatedOn());
                intent.putExtra("startTime",walletItem.getStartTime());
                intent.putExtra("endTime",walletItem.getEndTime());
                intent.putExtra("duration",walletItem.getWalletTransactionId());
                context.startActivity(intent);
            });

            int location = position;

            holder.binding.ratingReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.getPosition(location,walletItem.getUserId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return walletsModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        SingleChatListItemBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SingleChatListItemBinding.bind(itemView);
        }
    }

    public void setOnItemClick(OnItemClick onItemClick){
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void getPosition(int position,String userId);
    }
}
