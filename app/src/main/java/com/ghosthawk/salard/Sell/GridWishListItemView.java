package com.ghosthawk.salard.Sell;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghosthawk.salard.Data.Product;
import com.ghosthawk.salard.R;

/**
 * Created by Tacademy on 2016-05-20.
 */
public class GridWishListItemView extends FrameLayout {
    public GridWishListItemView(Context context) {
        super(context);
    }
    int img[] = {
            R.drawable.sample1, R.drawable.sample2, R.drawable.sample3,
            R.drawable.sample4, R.drawable.sample5, R.drawable.sample6,
            R.drawable.sample7, R.drawable.sample8, R.drawable.sample9,
            R.drawable.sample10};

    ImageView itemView;
    TextView soldView;
    private void init() {
        inflate(getContext(), R.layout.view_product_item,this);
        itemView = (ImageView)findViewById(R.id.img_item);
        soldView = (TextView)findViewById(R.id.img_sold_out);
    }


    public void setImageData(Product product){
        itemView.setImageResource(img[product.product_Picture]);
        if(product.product_Soldout){
            soldView.setVisibility(VISIBLE);
        }
    }
}
