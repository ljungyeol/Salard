package com.ghosthawk.salard.Sell;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ghosthawk.salard.Data.Member;
import com.ghosthawk.salard.Data.PackageProduct;
import com.ghosthawk.salard.Data.Product;
import com.ghosthawk.salard.R;

/**
 * Created by Tacademy on 2016-05-18.
 */
public class ProductViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView,imageView2,imageRank;
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
//        Typeface NS_regular = Typeface.createFromAsset(itemView.getContext().getAssets(),"NotoSansKR-Regular.otf");
//        Typeface NS_bold = Typeface.createFromAsset(itemView.getContext().getAssets(),"NotoSansKR-Bold.otf");
        imageRank = (ImageView)itemView.findViewById(R.id.img_rank);
        imageView = (ImageView)itemView.findViewById(R.id.image_product);
        imageView2 = (ImageView)itemView.findViewById(R.id.img_mem);
        nameView = (TextView)itemView.findViewById(R.id.text_name);
        priceView = (TextView)itemView.findViewById(R.id.text_price);
        countView = (TextView)itemView.findViewById(R.id.text_count);
//        nameView.setTypeface(NS_bold);
//        priceView.setTypeface(NS_regular);
//        this.nameView.setTypeface(NS_bold);
//        this.priceView.setTypeface(NS_regular);
//        this.countView.setTypeface(NS_regular);

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
        priceView.setText(+pack.getPackage_price()+"원");
        countView.setText(+pack.getPackage_count()+"개");
        Glide.with(imageView2.getContext()).load(pack.getPackage_personpicture()).into(imageView2);
        int img[]={
                R.drawable.rank0, R.drawable.rank1, R.drawable.rank2,
                R.drawable.rank3,R.drawable.rank4,R.drawable.rank5
        };
        int i = pack.getPerson_sellcount();
        if(i>0 && i<10){
            Glide.with(imageRank.getContext()).load(img[0]).into(imageRank);
        }
        else if(i<30){
            Glide.with(imageRank.getContext()).load(img[1]).into(imageRank);
        }
        else if(i<50){
            Glide.with(imageRank.getContext()).load(img[2]).into(imageRank);
        }
        else if(i<80){
            Glide.with(imageRank.getContext()).load(img[3]).into(imageRank);
        }
        else if(i<100){
            Glide.with(imageRank.getContext()).load(img[4]).into(imageRank);
        }
        else
            Glide.with(imageRank.getContext()).load(img[5]).into(imageRank);

    }



}
