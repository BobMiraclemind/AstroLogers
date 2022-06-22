package com.astroexpress.astrologer.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.databinding.SingleSuggestRemedyAttachmentBinding;
import com.bumptech.glide.Glide;

import java.util.List;

public class SuggestRemedyAttachmentAdapter extends RecyclerView.Adapter<SuggestRemedyAttachmentAdapter.AViewHolder> {
    private Context context;
    private List<Uri> imageUriList;
    OnItemClick onItemClick;

    public SuggestRemedyAttachmentAdapter(Context context, List<Uri> imageUriList) {
        this.context = context;
        this.imageUriList = imageUriList;
    }

    @NonNull
    @Override
    public AViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_suggest_remedy_attachment,parent,false);
        return new AViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AViewHolder holder, int position) {
        Uri imageUri = imageUriList.get(position);
        Glide.with(context)
                .load(imageUri)
                .placeholder(R.drawable.place_holder)
                .into(holder.binding.attachmentImage);
        int active = holder.getAdapterPosition();
        holder.binding.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.getPosition(active);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageUriList.size();
    }

    public class AViewHolder extends RecyclerView.ViewHolder {

        SingleSuggestRemedyAttachmentBinding binding;

        public AViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SingleSuggestRemedyAttachmentBinding.bind(itemView);
        }
    }

    public void setOnItemClick(OnItemClick onItemClick){
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void getPosition(int position);
    }
}
