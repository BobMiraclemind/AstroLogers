package com.astroexpress.astrologer.adapter;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.databinding.BlockAccountPopupBinding;
import com.astroexpress.astrologer.databinding.SingleBlockedAccountItemBinding;
import com.astroexpress.astrologer.utils.Utilities;

public class BlockedAccountAdapter extends RecyclerView.Adapter<BlockedAccountAdapter.ViewHolder> {

    private Context context;
    BlockAccountPopupBinding binding;

    public BlockedAccountAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_blocked_account_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rect r = Utilities.locateView(view);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                binding = BlockAccountPopupBinding.inflate(inflater);
                PopupWindow popupWindow = new PopupWindow(binding.getRoot(), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (popupWindow.isShowing()){
                    popupWindow.dismiss();
                }else {
                    popupWindow.setFocusable(true);
                    popupWindow.showAsDropDown(holder.binding.options,r.left,0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        SingleBlockedAccountItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SingleBlockedAccountItemBinding.bind(itemView);
        }
    }
}
