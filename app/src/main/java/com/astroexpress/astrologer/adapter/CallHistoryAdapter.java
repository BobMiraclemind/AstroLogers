package com.astroexpress.astrologer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.databinding.SingleCallHistoryItemBinding;
import com.astroexpress.astrologer.models.CallHistoryModel;

import java.util.ArrayList;

public class CallHistoryAdapter extends RecyclerView.Adapter<CallHistoryAdapter.MyViewHolder> {
    Context context;
    ArrayList<CallHistoryModel> callHistoryModelArrayList;

    public CallHistoryAdapter(Context context, ArrayList<CallHistoryModel> callHistoryModelArrayList) {
        this.context = context;
        this.callHistoryModelArrayList = callHistoryModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_call_history_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CallHistoryModel callHistoryModel = callHistoryModelArrayList.get(position);
        holder.binding.firstname.setText(callHistoryModel.getName());
        holder.binding.duration.setText(callHistoryModel.getDuration());
        holder.binding.time.setText(callHistoryModel.getTime());
    }

    @Override
    public int getItemCount() {
        return callHistoryModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        SingleCallHistoryItemBinding binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SingleCallHistoryItemBinding.bind(itemView);
        }
    }
}
