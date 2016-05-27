package com.ghosthawk.salard.Sell;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ghosthawk.salard.Data.PackageProduct;
import com.ghosthawk.salard.Data.Product;
import com.ghosthawk.salard.R;

/**
 * Created by Tacademy on 2016-05-18.
 */
public class ProductViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView,imageView2;
    TextView nameView, priceView, countView;
    PackageProduct pack;


    public interface OnItemClickListener {
        public void onItemClick(View view, PackageProduct pack);
    }
    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public ProductViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView)itemView.findViewById(R.id.image_product);
        imageView2 = (ImageView)itemView.findViewById(R.id.img_mem_pic);
        nameView = (TextView)itemView.findViewById(R.id.text_name);
        priceView = (TextView)itemView.findViewById(R.id.text_price);
        countView = (TextView)itemView.findViewById(R.id.text_count);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    mListener.onItemClick(v,pack);
                }

            }
        });
    }


    public void setPackage(PackageProduct pack) {
        this.pack = pack;

        Glide.with(imageView.getContext()).load(pack.getPackage_mainpicture()).into(imageView);
        //imageView.setImageBitmap(pack.getBm());


        //imageView.setImageResource(Integer.parseInt(pack.getPackage_mainpicture()));
        nameView.setText(pack.getPackage_name());
        priceView.setText("/"+pack.getPackage_price()+"원");
        countView.setText(+pack.getPackage_count()+"개");
        Glide.with(imageView2.getContext()).load(pack.getPackage_personpicture()).into(imageView2);
    }

}
