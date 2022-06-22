package com.astroexpress.astrologer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.databinding.SingleRaisedTicketItemBinding;

public class RaisedTicketAdapter extends RecyclerView.Adapter<RaisedTicketAdapter.ItemViewHolder> {

    private Context context;

    public RaisedTicketAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_raised_ticket_item,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        SingleRaisedTicketItemBinding binding;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SingleRaisedTicketItemBinding.bind(itemView);
        }
    }
}
