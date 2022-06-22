package com.astroexpress.astrologer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.databinding.SingleSuggestedRemedyItemBinding;
import com.astroexpress.astrologer.models.response.SuggestedRemedyModel;

import java.util.List;

public class SuggestedRemedyAdapter extends RecyclerView.Adapter<SuggestedRemedyAdapter.MyViewHolder> {
    Context context;
    List<SuggestedRemedyModel.Result> list;

    public SuggestedRemedyAdapter(Context context, List<SuggestedRemedyModel.Result> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_suggested_remedy_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SuggestedRemedyModel.Result data = list.get(position);
        holder.binding.category.setText(data.getCategoryName());
        holder.binding.productName.setText(data.getProductName());
        holder.binding.name.setText(data.getFirstName());
        holder.binding.userId.setText(data.getUserId());
        holder.binding.dateTime.setText(data.getCreatedOn());
        holder.binding.price.setText(data.getPrice());
        holder.binding.remedyStatus.setText(data.getBookingStatus());
        if (data.getBookingStatus().matches("Not Booked")){
            holder.binding.remedyStatus.setTextColor(context.getResources().getColor(R.color.red));
        }else if (data.getBookingStatus().matches("Booked")){
            holder.binding.remedyStatus.setTextColor(context.getResources().getColor(R.color.dark_green));
        }
        if (data.getPrice().equals("0")){
            holder.binding.remedyType.setText("Free Remedy");
        }else {
            holder.binding.remedyType.setText("Paid Remedy");
        }
        holder.binding.description.setText(data.getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        SingleSuggestedRemedyItemBinding binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SingleSuggestedRemedyItemBinding.bind(itemView);
        }
    }
}
