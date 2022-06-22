package com.astroexpress.astrologer.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.databinding.SingleBlogItemBinding;
import com.astroexpress.astrologer.models.response.TestimonialModels;
import com.bumptech.glide.Glide;

import java.util.List;

public class BlogsAdapter extends RecyclerView.Adapter<BlogsAdapter.MyViewHolder> {

    private Context context;
    private List<TestimonialModels.Result> results;

    public BlogsAdapter(Context context, List<TestimonialModels.Result> results) {
        this.context = context;
        this.results = results;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_blog_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TestimonialModels.Result testimonialModel = results.get(position);
        Glide.with(context)
                .load(testimonialModel.getFileUrl())
                .thumbnail(Glide.with(context).load(R.drawable.image_place_holder))
                .centerCrop()
                .into(holder.binding.image);
        holder.binding.title.setText(testimonialModel.getTitle());
        holder.binding.description.setText(testimonialModel.getDescription());
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(blogsModel.getVideoLink())));
                Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse(testimonialModel.getVideoUrl()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (results.size()>7){
            return 7;
        }else {
            return results.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        SingleBlogItemBinding binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SingleBlogItemBinding.bind(itemView);
        }
    }
}
