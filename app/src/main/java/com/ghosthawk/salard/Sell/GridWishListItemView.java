package com.ghosthawk.salard.Sell;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ghosthawk.salard.Data.PackageProduct;
import com.ghosthawk.salard.Data.Product;
import com.ghosthawk.salard.R;

import org.w3c.dom.Text;

/**
 * Created by Tacademy on 2016-05-20.
 */
public class GridWishListItemView extends FrameLayout {
    public GridWishListItemView(Context context) {
        super(context);
        init();
    }
    int img[] = {
            R.drawable.sample1, R.drawable.sample2, R.drawable.sample3,
            R.drawable.sample4, R.drawable.sample5, R.drawable.sample6,
            R.drawable.sample7, R.drawable.sample8, R.drawable.sample9,
            R.drawable.sample10};

    public interface OnItemClickListener {
        public void onItemClick(View view, PackageProduct pack);
    }
    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    ImageView itemView,memView;
    TextView soldView,textName,textPrice,textCount,textLoca;
    private void init() {
        inflate(getContext(), R.layout.view_product_item,this);
        itemView = (ImageView)findViewById(R.id.img_item);
        soldView = (TextView)findViewById(R.id.img_sold_out);
        memView = (ImageView)findViewById(R.id.img_mem);
        textName=(TextView)findViewById(R.id.text_name);
        textPrice = (TextView)findViewById(R.id.text_price);
        textCount = (TextView)findViewById(R.id.text_count);
        textLoca =(TextView)findViewById(R.id.text_loca);
    }


    public void setImageData(PackageProduct packageProduct){
        Glide.with(itemView.getContext()).load(packageProduct.package_mainpicture).into(itemView);
        Glide.with(memView.getContext()).load(packageProduct.package_personpicture).into(memView);
        textName.setText(packageProduct.getPackage_name());
        textPrice.setText(packageProduct.getPackage_price()+"원");
        textCount.setText(packageProduct.getPackage_count()+"개");
        textLoca.setText(packageProduct.getPackage_loca());
        if(packageProduct.package_state==1){
            soldView.setVisibility(VISIBLE);
        }
    }
}
