package com.astroexpress.astrologer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.databinding.SingleCallListItemBinding;
import com.astroexpress.astrologer.models.response.WalletsModel;

import java.util.List;

public class CallDetailAdapter extends RecyclerView.Adapter<CallDetailAdapter.ViewHolder> {

    private Context context;
    private List<WalletsModel.Result> resultList;

    public CallDetailAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_call_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        SingleCallListItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SingleCallListItemBinding.bind(itemView);
        }
    }
}
