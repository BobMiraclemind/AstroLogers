package com.astroexpress.astrologer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.databinding.SingleRecentUserItemBinding;
import com.astroexpress.astrologer.models.response.UsersModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.ImageViewTarget;

import java.util.List;

public class RecentUserAdapter extends RecyclerView.Adapter<RecentUserAdapter.MyViewHolder> {

    Context context;
    List<UsersModel.Result> recentUserModels;

    public RecentUserAdapter(Context context, List<UsersModel.Result> recentUserModels) {
        this.context = context;
        this.recentUserModels = recentUserModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_recent_user_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UsersModel.Result recentUserModel = recentUserModels.get(position);
        Glide.with(context)
                .asGif()
                .load(recentUserModel.getProfileImageUrl())
                .placeholder(R.drawable.place_holder)
                .centerCrop()
                .into(holder.binding.profileImg);
        holder.binding.name.setText(recentUserModel.getFirstname());
    }

    @Override
    public int getItemCount() {
        return recentUserModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        SingleRecentUserItemBinding binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SingleRecentUserItemBinding.bind(itemView);
        }
    }
}
