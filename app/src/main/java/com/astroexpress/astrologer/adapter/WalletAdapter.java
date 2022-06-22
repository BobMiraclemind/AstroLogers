package com.astroexpress.astrologer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.databinding.SingleWalletItemBinding;
import com.astroexpress.astrologer.models.response.WalletsModel;

import java.util.List;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.MyViewHolder> {

    Context context;
    List<WalletsModel.Result> walletModelArrayList;

    public WalletAdapter(Context context, List<WalletsModel.Result> walletModelArrayList) {
        this.context = context;
        this.walletModelArrayList = walletModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_wallet_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        WalletsModel.Result walletModel = walletModelArrayList.get(position);
        holder.binding.date.setText(walletModel.getCreatedOn());
//        holder.binding.time.setText(walletModel.getTime());
        holder.binding.transactionId.setText(walletModel.getWalletTransactionId());
        holder.binding.firstname.setText(walletModel.getFirstname());
        holder.binding.lastname.setText(walletModel.getLastname());
        holder.binding.serviceType.setText(walletModel.getTransactionFor() == null ? "Service" : walletModel.getTransactionFor());
        holder.binding.price.setText(walletModel.getAmount());
    }

    @Override
    public int getItemCount() {
        return walletModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        SingleWalletItemBinding binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SingleWalletItemBinding.bind(itemView);
        }
    }
}
