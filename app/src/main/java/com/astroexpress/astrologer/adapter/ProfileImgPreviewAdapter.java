package com.astroexpress.astrologer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.models.response.AstrologerContentModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class ProfileImgPreviewAdapter extends PagerAdapter {

    private Context context;
    private List<AstrologerContentModel.Result> profilePreviewModels;

    public ProfileImgPreviewAdapter(Context context, List<AstrologerContentModel.Result> profilePreviewModels) {
        this.context = context;
        this.profilePreviewModels = profilePreviewModels;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_image_preview_item,container,false);
        ImageView imageView = view.findViewById(R.id.preview_img);
        Glide.with(context)
                .load(profilePreviewModels.get(position).getFileUrls())
                .into(imageView);

        container.addView(view,0);
        return view;
    }

    @Override
    public int getCount() {
        return profilePreviewModels.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
