package com.ghosthawk.salard.Sell;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghosthawk.salard.Data.Product;
import com.ghosthawk.salard.R;

/**
 * Created by Tacademy on 2016-05-18.
 */
public class ProductViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView nameView, priceView, countView;

    Product product;

    public interface OnItemClickListener {
        public void onItemClick(View view, Product product);
    }
    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public ProductViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView)itemView.findViewById(R.id.image_product);
        nameView = (TextView)itemView.findViewById(R.id.text_name);
        priceView = (TextView)itemView.findViewById(R.id.text_price);
        countView = (TextView)itemView.findViewById(R.id.text_count);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    mListener.onItemClick(v,product);
                }

            }
        });
    }


    public void setProduct(Product product) {
        this.product = product;

       // Glide.with(thumbView.getContext()).load(product.getThumbnailUrl()).into(thumbView);
        imageView.setImageResource(product.getProduct_Picture());
        nameView.setText(product.getProduct_Name());
        priceView.setText("/"+product.getProduct_Price()+"원");
        countView.setText("/"+product.getProduct_Count()+"개");
    }

}
