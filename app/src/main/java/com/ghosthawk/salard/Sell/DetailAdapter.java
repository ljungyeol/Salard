package com.ghosthawk.salard.Sell;

import android.content.Context;
import android.media.Image;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ghosthawk.salard.Data.PackageProduct;
import com.ghosthawk.salard.R;

import java.util.zip.Inflater;

/**
 * Created by Tacademy on 2016-06-04.
 */
public class DetailAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    ImageView imageView;
    TextView textName,textPrice,textCount;
    public DetailAdapter(Context context) {
        this.context = context;
//        options = new BitmapFactory.Options();
    }

    public DetailAdapter(LayoutInflater inflater) {


        //전달 받은 LayoutInflater를 멤버변수로 전달

        this.layoutInflater=inflater;

    }



    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
    PackageProduct packageproduct = new PackageProduct();

    public void add(PackageProduct pack){
        packageproduct = pack;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.view_product_detail,null);
        imageView = (ImageView)view.findViewById(R.id.imageView2);
        textName = (TextView)view.findViewById(R.id.text_name);
        textPrice=(TextView)view.findViewById(R.id.text_price);
        textCount=(TextView)view.findViewById(R.id.text_count);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if(position==0){
            Glide.with(imageView.getContext()).load(packageproduct.getPackage_mainpicture()).into(imageView);
        }
        if(position==1){
            Glide.with(imageView.getContext()).load(packageproduct.getPackage_subpicture()).into(imageView);
        }
        textName.setText(packageproduct.getPackage_name());
        textPrice.setText(packageproduct.getPackage_price()+"원");
        textCount.setText(packageproduct.getPackage_count()+"개");
        container.addView(view);
        return view;
    }
    @Override
    public int getCount() {
        return 2;
    }
    @Override

    public void destroyItem(ViewGroup container, int position, Object object) {



        //ViewPager에서 보이지 않는 View는 제거

        //세번째 파라미터가 View 객체 이지만 데이터 타입이 Object여서 형변환 실시

        container.removeView((View)object);



    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
