package com.astroexpress.astrologer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.PagerAdapter;

import com.astroexpress.astrologer.R;
import com.astroexpress.astrologer.models.response.BannersModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class BannerSlideAdapter  extends PagerAdapter {

    private Context context;
    private List<BannersModel.Result> bannerSlideModels;

    public BannerSlideAdapter(Context context, List<BannersModel.Result> bannerSlideModels) {
        this.context = context;
        this.bannerSlideModels = bannerSlideModels;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.banner_slide_layout,container,false);
//        return super.instantiateItem(container, position);
        AppCompatImageView imageView = view.findViewById(R.id.banner_image);
        Glide.with(view)
                .load(bannerSlideModels.get(position).getFileUrl())
                .thumbnail(Glide.with(context).load(R.drawable.image_place_holder))
                .centerCrop()
                .into(imageView);
        container.addView(view,0);

        /*//redirection to web on click
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(bannerSlideModels.get(position).getRedirectURL()));
                browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(browserIntent);
            }
        });*/

        return view;
    }

    @Override
    public int getCount() {
        return bannerSlideModels.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
