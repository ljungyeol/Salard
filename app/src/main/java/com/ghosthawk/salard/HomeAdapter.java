package com.ghosthawk.salard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ghosthawk.salard.R;

/**
 * Created by Tacademy on 2016-05-27.
 */
public class HomeAdapter extends PagerAdapter {

//    List<String> items = new ArrayList<String>();
//
//    List<View> scrapped = new ArrayList<View>();

    Context context;

    private final int[] galImages = new int[] {
            R.drawable.service_image_1,
            R.drawable.service_image_2,
            R.drawable.service_image_3,
            R.drawable.service_image_4,
    };

    public HomeAdapter(Context context) {
        this.context = context;
//        options = new BitmapFactory.Options();
    }

    @Override
    public int getCount() {
        return galImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView imageView = new ImageView(context);
//            int padding = context.getResources().getDimensionPixelSize();
//            imageView.setPadding(padding, padding, padding, padding);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(imageView.getContext()).load(galImages[position]).into(imageView);

//        options.inSampleSize = 4;
//        galImage = BitmapFactory.decodeResource(context.getResources(), galImages[position], options);
//
//        imageView.setImageBitmap(galImage);
        ((ViewPager) container).addView(imageView, 0);

        return imageView;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
}