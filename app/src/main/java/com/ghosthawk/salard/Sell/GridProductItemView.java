package com.ghosthawk.salard.Sell;

import android.content.Context;
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
public class GridProductItemView extends FrameLayout {
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



    public GridProductItemView(Context context) {
        super(context);
        init();
    }
    ImageView itemView;
    TextView soldView,textName,textCount,textPrice,textLoca;
    private void init() {
        inflate(getContext(), R.layout.view_product_item,this);
        itemView = (ImageView)findViewById(R.id.img_item);
        soldView = (TextView)findViewById(R.id.img_sold_out);
        textName = (TextView)findViewById(R.id.text_name);
        textCount = (TextView)findViewById(R.id.text_count);
        textPrice = (TextView)findViewById(R.id.text_price);
        textLoca=(TextView)findViewById(R.id.text_loca);
        textName.setVisibility(GONE);
        textCount.setVisibility(GONE);
        textPrice.setVisibility(GONE);
        textLoca.setVisibility(GONE);


    }


    public void setImageData(PackageProduct packageProduct){
        Glide.with(itemView.getContext()).load(packageProduct.package_mainpicture).into(itemView);

        if(packageProduct.package_state==1){
            soldView.setVisibility(VISIBLE);
        }
    }
}
