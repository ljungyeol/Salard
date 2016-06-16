package com.ghosthawk.salard.Sell;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ghosthawk.salard.Data.PackageProduct;
import com.ghosthawk.salard.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-06-11.
 */
public class ProductDetailPagerAdapter extends PagerAdapter {
    PackageProduct pack;
    Context context;
    @Override
    public int getCount() {
        if (pack == null) return 0;
        return 2;
    }
    public ProductDetailPagerAdapter(Context context) {
        this.context = context;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    public void add(PackageProduct p){
        this.pack = p;
        notifyDataSetChanged();
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView imageView = new ImageView(context);
//            int padding = context.getResources().getDimensionPixelSize();
//            imageView.setPadding(padding, padding, padding, padding);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if(position==0){
            Glide.with(imageView.getContext()).load(pack.getPackage_mainpicture()).into(imageView);
//            Glide.with(imageView.getContext()).load(R.drawable.sample1).into(imageView);
        }
        if(position==1){
            Glide.with(imageView.getContext()).load(pack.getPackage_subpicture()).into(imageView);
        }


//        options.inSampleSize = 4;
//        galImage = BitmapFactory.decodeResource(context.getResources(), galImages[position], options);
//
//        imageView.setImageBitmap(galImage);
        ((ViewPager) container).addView(imageView, 0);

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }


}
