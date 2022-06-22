package com.astroexpress.astrologer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.databinding.SingleProfileGalleryItemBinding;
import com.astroexpress.astrologer.models.response.AstrologerContentModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class ProfileGalleryAdapter extends RecyclerView.Adapter<ProfileGalleryAdapter.ImageViewHolder> {

    private Context context;
    private List<AstrologerContentModel.Result> profileGalleryModels;
    OnItemClick onItemClick;

    public ProfileGalleryAdapter(Context context, List<AstrologerContentModel.Result> profileGalleryModels) {
        this.context = context;
        this.profileGalleryModels = profileGalleryModels;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_profile_gallery_item,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        AstrologerContentModel.Result content = profileGalleryModels.get(position);
        Glide.with(context)
                .load(content.getFileUrls())
                .centerCrop()
                .placeholder(R.drawable.place_holder)
                .into(holder.binding.galleryImg);
        int active = position;
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.getPosition(active);
            }
        });
    }

    @Override
    public int getItemCount() {
        return profileGalleryModels.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        SingleProfileGalleryItemBinding binding;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SingleProfileGalleryItemBinding.bind(itemView);
        }
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void getPosition(int position); //pass any things
    }
}
