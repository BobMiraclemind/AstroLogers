package com.astroexpress.astrologer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.databinding.SingleNextOnlineItemBinding;

public class NextOnlineTimeAdapter extends RecyclerView.Adapter<NextOnlineTimeAdapter.Viewholder> {

    private Context context;

    public NextOnlineTimeAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public NextOnlineTimeAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_next_online_item,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NextOnlineTimeAdapter.Viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        SingleNextOnlineItemBinding binding;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            binding = SingleNextOnlineItemBinding.bind(itemView);
        }
    }
}
