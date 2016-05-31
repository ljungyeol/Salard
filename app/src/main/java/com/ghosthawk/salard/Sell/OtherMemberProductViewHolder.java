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
public class OtherMemberProductViewHolder extends RecyclerView.ViewHolder {
    ImageView itemView;
    TextView soldView,textName,textCount,textPrice,textLoca;
    PackageProduct packageProduct;
    public interface OnItemClickListener {
        public void onItemClick(View view, PackageProduct pack);
    }
    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }




    public OtherMemberProductViewHolder(View itemView) {
        super(itemView);
        this.itemView = (ImageView)itemView.findViewById(R.id.img_item);
        soldView = (TextView)itemView.findViewById(R.id.img_sold_out);
        textName = (TextView)itemView.findViewById(R.id.text_name);
        textCount = (TextView)itemView.findViewById(R.id.text_count);
        textPrice = (TextView)itemView.findViewById(R.id.text_price);
        textLoca=(TextView)itemView.findViewById(R.id.text_loca);
        textName.setVisibility(View.GONE);
        textPrice.setVisibility(View.GONE);
        textCount.setVisibility(View.GONE);
        textLoca.setVisibility(View.GONE);

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
        if(packageProduct!=null) {
            this.packageProduct = packageProduct;
            Glide.with(itemView.getContext()).load(packageProduct.package_mainpicture).into(itemView);

            if (packageProduct.package_state == 1) {
                soldView.setVisibility(View.VISIBLE);
            }
        }
    }
}
