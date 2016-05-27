package com.ghosthawk.salard.Sell;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ghosthawk.salard.Data.PackageProduct;
import com.ghosthawk.salard.R;

/**
 * Created by Tacademy on 2016-05-20.
 */
public class GridAddListItemView extends FrameLayout{
    public GridAddListItemView(Context context) {
        super(context);
        init();
    }
    ImageView itemView, memView;
    TextView soldView, textName, textPrice, textCount, textLoca;

    private void init() {
        inflate(getContext(), R.layout.view_product_item,this);
        itemView = (ImageView)findViewById(R.id.img_item);
        soldView = (TextView)findViewById(R.id.img_sold_out);
        textName=(TextView)findViewById(R.id.text_name);
        textPrice = (TextView)findViewById(R.id.text_price);
        textCount = (TextView)findViewById(R.id.text_count);

    }


    public void setImageData(PackageProduct packageProduct){
        Glide.with(itemView.getContext()).load(packageProduct.package_mainpicture).into(itemView);
        textName.setText(packageProduct.getPackage_name());
        textPrice.setText(packageProduct.getPackage_price()+"원");
        textCount.setText(packageProduct.getPackage_count()+"개");
        if(packageProduct.package_state==1){
            soldView.setVisibility(VISIBLE);
        }
    }
}
