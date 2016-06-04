package com.ghosthawk.salard.Sell;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ghosthawk.salard.Data.PackageProduct;
import com.ghosthawk.salard.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-05-27.
 */
public class OtherMemberProductAdapter extends RecyclerView.Adapter<OtherMemberProductViewHolder>{



    List<PackageProduct> items = new ArrayList<>();

    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }

    public void add(PackageProduct packageProduct){
        items.add(packageProduct);
        notifyDataSetChanged();
    }

    public void addAll(List<PackageProduct> items){
        this.items.addAll(items);
        notifyDataSetChanged();

    }

    OtherMemberProductViewHolder.OnItemClickListener mListener;
    public void setOnItemClickListener( OtherMemberProductViewHolder.OnItemClickListener listener){
        mListener = listener;
    }


    @Override
    public OtherMemberProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_other_product_item, parent, false);
        return new OtherMemberProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OtherMemberProductViewHolder holder, int position) {
        holder.setImageData(items.get(position));
        holder.setOnItemClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
