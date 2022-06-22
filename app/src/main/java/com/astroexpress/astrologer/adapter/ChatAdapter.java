package com.astroexpress.astrologer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.databinding.IncomingMsgLayoutBinding;
import com.astroexpress.astrologer.databinding.OutgoingMsgLayoutBinding;
import com.astroexpress.astrologer.models.ChatModel;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<ChatModel> chatModels;

    final int ITEM_SENT = 1;
    final int ITEM_RECEIVE = 2;

    public ChatAdapter(Context context, ArrayList<ChatModel> chatModels) {
        this.context = context;
        this.chatModels = chatModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_SENT){
            View view = LayoutInflater.from(context).inflate(R.layout.outgoing_msg_layout,parent,false);
            return new SenderViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.incoming_msg_layout,parent,false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        ChatModel chatModel = chatModels.get(position);
        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(chatModel.getUid())){
            return ITEM_SENT;
        }else {
            return ITEM_RECEIVE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatModel chatModel = chatModels.get(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
        if (holder.getClass() == SenderViewHolder.class){
            SenderViewHolder viewHolder = (SenderViewHolder) holder;
            viewHolder.outgoingMsgLayoutBinding.message.setText(chatModel.getMessage());
            viewHolder.outgoingMsgLayoutBinding.time.setText(dateFormat.format(Long.valueOf(chatModel.getTime())));
        }else {
            ReceiverViewHolder viewHolder = (ReceiverViewHolder) holder;
            viewHolder.incomingMsgLayoutBinding.message.setText(chatModel.getMessage());
            viewHolder.incomingMsgLayoutBinding.time.setText(dateFormat.format(Long.valueOf(chatModel.getTime())));
        }
    }

    @Override
    public int getItemCount() {
        return chatModels.size();
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {

        OutgoingMsgLayoutBinding outgoingMsgLayoutBinding;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            outgoingMsgLayoutBinding = OutgoingMsgLayoutBinding.bind(itemView);
        }
    }
    public class ReceiverViewHolder extends RecyclerView.ViewHolder{

        IncomingMsgLayoutBinding incomingMsgLayoutBinding;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            incomingMsgLayoutBinding = IncomingMsgLayoutBinding.bind(itemView);
        }
    }
}
