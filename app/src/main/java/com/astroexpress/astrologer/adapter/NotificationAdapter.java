package com.astroexpress.astrologer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.databinding.SingleWaitlistItemBinding;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.Viewholder> {

    private Context context;

    public NotificationAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_waitlist_item,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        SingleWaitlistItemBinding binding;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            binding = SingleWaitlistItemBinding.bind(itemView);
        }
    }
}
