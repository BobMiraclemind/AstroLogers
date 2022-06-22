package com.astroexpress.astrologer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.databinding.SingleChatUserItemBinding;
import com.astroexpress.astrologer.models.response.UsersModel;
import com.astroexpress.astrologer.ui.activities.ChatDetailActivity;
import com.bumptech.glide.Glide;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyViewHolder> {

    Context context;
    List<UsersModel.Result> chatListModels;

    public ChatListAdapter(Context context, List<UsersModel.Result> chatListModels) {
        this.context = context;
        this.chatListModels = chatListModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_chat_user_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UsersModel.Result chatListModel = chatListModels.get(position);
        holder.binding.lastMessage.setText(chatListModel.getLastChatMessage()==null?"":chatListModel.getLastChatMessage());

        Glide.with(context)
                .load(chatListModel.getProfileImageUrl())
                .centerCrop()
                .placeholder(R.drawable.place_holder)
                .into(holder.binding.profileImg);
        holder.binding.firstname.setText(chatListModel.getFirstname()==null?"":chatListModel.getFirstname());
        holder.binding.lastname.setText(chatListModel.getLastName()==null?"":chatListModel.getLastName());
        holder.binding.time.setText(chatListModel.getCreatedOn()==null?"":chatListModel.getCreatedOn());

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, ChatDetailActivity.class);
                intent.putExtra("profile_img",chatListModel.getProfileImageUrl()==null?"":chatListModel.getProfileImageUrl());
                intent.putExtra("uid",chatListModel.getUserId()==null?"":chatListModel.getUserId());
                intent.putExtra("firstname",chatListModel.getFirstname()==null?"":chatListModel.getFirstname());
                intent.putExtra("lastname",chatListModel.getLastName()==null?"":chatListModel.getLastName());
                intent.putExtra("last_msg",chatListModel.getLastChatMessage()==null?"":chatListModel.getLastChatMessage());
                intent.putExtra("time",chatListModel.getCreatedOn());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatListModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        SingleChatUserItemBinding binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SingleChatUserItemBinding.bind(itemView);
        }
    }
}
