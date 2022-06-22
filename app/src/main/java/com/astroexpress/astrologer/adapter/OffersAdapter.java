package com.astroexpress.astrologer.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.databinding.SingleOfferItemBinding;
import com.astroexpress.astrologer.models.response.ApplyOfferResponseModel;
import com.astroexpress.astrologer.models.response.OfferResponseModel;
import com.astroexpress.astrologer.network.RetrofitClient;
import com.astroexpress.astrologer.utils.StaticFields;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {

    private static final String TAG = "offers";
    private Context context;
    private List<OfferResponseModel.Result> offersModel;
    private int i,j,k;
//    private int selectedPosition = -1;
    private ArrayList<Boolean> selectCheck = new ArrayList<>();

    public OffersAdapter(Context context, List<OfferResponseModel.Result> offersModel) {
        this.context = context;
        this.offersModel = offersModel;

        for (int i = 0;i<offersModel.size();i++){
            selectCheck.add(offersModel.get(i).getOfferApplied());
            Log.i(TAG, "OffersAdapter: "+offersModel.get(i).getOfferApplied());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_offer_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
        OfferResponseModel.Result offer = offersModel.get(position);
        i = Integer.parseInt(StaticFields.astrologerData.getChargePerMin());
        j = Integer.parseInt(offer.getDiscountAmount()==null?"":offer.getDiscountAmount());
        k = i-j;
        holder.binding.title.setText(offer.getTitle()==null?"":offer.getTitle());
        holder.binding.description.setText(offer.getDescription()==null?"":offer.getDescription());
        holder.binding.price.setText(StaticFields.astrologerData.getChargePerMin());
        holder.binding.effectivePrice.setText(Integer.toString(k));

        if (selectCheck.get(position) == true) {
            holder.binding.checkBox.setChecked(true);
        } else {
            holder.binding.checkBox.setChecked(false);
        }

        holder.binding.getRoot().setOnClickListener(view -> {
            if (!holder.binding.checkBox.isChecked()){
                holder.binding.checkBox.setChecked(true);
                for(int k=0; k<selectCheck.size(); k++) {
                    if(k==position) {
                        selectCheck.set(k,true);
                        Log.i(TAG, " apiCall onBindHolder: "+StaticFields.astrologerData.getAstrologerId()+" "+true+" "+offersModel.get(k).getOfferId());
                        callApplyOffer(StaticFields.astrologerData.getAstrologerId(),true,offersModel.get(k).getOfferId());
                        Toast.makeText(context.getApplicationContext(),offersModel.get(k).getTitle()+" Applied",Toast.LENGTH_SHORT).show();
//                        Log.i(TAG, "check: "+selectCheck.get(k)+" "+k);
                    } else {
                        selectCheck.set(k,false);
                        if (selectCheck.get(k).equals(true)){
                            Log.i(TAG, "apiCall onViewHolder: "+StaticFields.astrologerData.getAstrologerId()+" "+false+" "+offersModel.get(k).getOfferApplied());
                            callApplyOffer(StaticFields.astrologerData.getAstrologerId(),false,offersModel.get(k).getOfferId());
                        }
                        Log.i(TAG, "uncheck: "+selectCheck.get(k)+" "+k);
                    }
                }
            }else {
                holder.binding.checkBox.setChecked(false);
                selectCheck.set(position,false);
                Log.i(TAG, "onHolder: "+StaticFields.astrologerData.getAstrologerId()+" "+false+" "+offersModel.get(position).getOfferId());
                callApplyOffer(StaticFields.astrologerData.getAstrologerId(),false,"0");
                Toast.makeText(context.getApplicationContext(),offersModel.get(position).getTitle()+" Removed",Toast.LENGTH_SHORT).show();

                for (int a = 0;a<selectCheck.size();a++){
                    Log.i(TAG, "uncheck same: " +selectCheck.get(a)+" "+a);
                }
            }
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return offersModel==null?0: offersModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        SingleOfferItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SingleOfferItemBinding.bind(itemView);
        }
    }

    private void callApplyOffer(String astrologerId,boolean isOfferApplied,String offerId) {
        RetrofitClient.getApiClient().applyOffer(astrologerId,isOfferApplied,offerId).enqueue(new Callback<ApplyOfferResponseModel>() {
            @Override
            public void onResponse(Call<ApplyOfferResponseModel> call, Response<ApplyOfferResponseModel> response) {
                try {
                    if (response.isSuccessful() && response.body() != null){
                        if (response.body().getCode().equals("200")){
                            Toast.makeText(context.getApplicationContext(), response.message(),Toast.LENGTH_SHORT);
                        }
                    }
                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<ApplyOfferResponseModel> call, Throwable t) {

            }
        });
    }
}
