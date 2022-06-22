package com.astroexpress.astrologer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.databinding.SingleWalletItemBinding;
import com.astroexpress.astrologer.models.response.EarningResponseModel;

import java.util.List;

public class WalletEarningAdapter extends RecyclerView.Adapter<WalletEarningAdapter.EarnViewHolder> {

    private Context context;
    private List<EarningResponseModel.Result> earningResponseModels;

    public WalletEarningAdapter(Context context, List<EarningResponseModel.Result> earningResponseModels) {
        this.context = context;
        this.earningResponseModels = earningResponseModels;
    }

    @NonNull
    @Override
    public EarnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_wallet_item,parent,false);
        return new EarnViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EarnViewHolder holder, int position) {
        EarningResponseModel.Result earning = earningResponseModels.get(position);
        holder.binding.firstname.setText(earning.getUserFirstName());
        holder.binding.lastname.setText(earning.getUserLastName());
        holder.binding.transactionId.setText(earning.getWalletTransactionId());
        holder.binding.serviceType.setText(earning.getTransactionFor()==null?"Service":earning.getTransactionFor());
        holder.binding.date.setText(earning.getCreatedOn());
        holder.binding.price.setText(earning.getAmount());

    }

    @Override
    public int getItemCount() {
        return earningResponseModels.size();
    }

    public class EarnViewHolder extends RecyclerView.ViewHolder {

        SingleWalletItemBinding binding;

        public EarnViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SingleWalletItemBinding.bind(itemView);
        }
    }
}
