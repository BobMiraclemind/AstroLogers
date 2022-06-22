package com.astroexpress.astrologer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.databinding.SingleRecentInteractionItemBinding;
import com.astroexpress.astrologer.models.response.UsersModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class RecentInteractionAdapter extends RecyclerView.Adapter<RecentInteractionAdapter.MyViewHolder> {
    Context context;
    List<UsersModel.Result> recentInteractionModels;

    public RecentInteractionAdapter(Context context, List<UsersModel.Result> recentInteractionModels) {
        this.context = context;
        this.recentInteractionModels = recentInteractionModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_recent_interaction_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UsersModel.Result recentInteraction = recentInteractionModels.get(position);
        Glide.with(context)
                .load(recentInteraction.getProfileImageUrl())
                .centerCrop()
                .placeholder(R.drawable.place_holder)
                .into(holder.binding.userImg);
        holder.binding.firstname.setText(recentInteraction.getFirstname());
        holder.binding.lastname.setText(recentInteraction.getLastName());
//        holder.binding.problems.setText(recentInteraction.getProblems());
    }

    @Override
    public int getItemCount() {
        return recentInteractionModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        SingleRecentInteractionItemBinding binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SingleRecentInteractionItemBinding.bind(itemView);
        }
    }
}
