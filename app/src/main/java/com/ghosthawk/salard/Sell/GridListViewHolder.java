package com.ghosthawk.salard.Sell;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ghosthawk.salard.Data.PackageProduct;
import com.ghosthawk.salard.R;

/**
 * Created by Tacademy on 2016-05-27.
 */
public class GridListViewHolder extends RecyclerView.ViewHolder {
    ImageView itemView,memView;
    TextView soldView,textName,textPrice,textCount,textLoca;
    PackageProduct packageProduct;


    public interface OnItemClickListener {
        public void onItemClick(View view, PackageProduct packageProduct);
    }
    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }





    public GridListViewHolder(View itemView) {
        super(itemView);
        this.itemView = (ImageView)itemView.findViewById(R.id.img_item);
        soldView = (TextView)itemView.findViewById(R.id.img_sold_out);
        memView = (ImageView)itemView.findViewById(R.id.img_mem);
        textName=(TextView)itemView.findViewById(R.id.text_name);
        textPrice = (TextView)itemView.findViewById(R.id.text_price);
        textCount = (TextView)itemView.findViewById(R.id.text_count);
        textLoca =(TextView)itemView.findViewById(R.id.text_loca);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    mListener.onItemClick(v,packageProduct);
                }

            }
        });
    }

    public void setImageData(PackageProduct packageProduct){
        this.packageProduct = packageProduct;
        Glide.with(itemView.getContext()).load(packageProduct.package_mainpicture).into(itemView);
        Glide.with(memView.getContext()).load(packageProduct.package_personpicture).into(memView);
        textName.setText(packageProduct.getPackage_name());
        textPrice.setText(packageProduct.getPackage_price()+"원");
        textCount.setText(packageProduct.getPackage_count()+"개");
        textLoca.setText(packageProduct.getPackage_loca());
        if(packageProduct.package_state==1){
            soldView.setVisibility(View.VISIBLE);
        }
    }
}
