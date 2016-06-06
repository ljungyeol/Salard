package com.ghosthawk.salard.Sell;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ghosthawk.salard.Data.PackageProduct;
import com.ghosthawk.salard.Data.Product;
import com.ghosthawk.salard.Message.MessageViewHolder;
import com.ghosthawk.salard.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-05-20.
 */
public class GridWishListAdapter extends RecyclerView.Adapter<GridWishListViewHolder> {
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

    GridWishListViewHolder.OnItemClickListener mListener;
    public void setOnItemClickListener(GridWishListViewHolder.OnItemClickListener listener){
        mListener = listener;
    }



    @Override
    public GridWishListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_wish_item,null);
        return new GridWishListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GridWishListViewHolder holder, int position) {
        holder.setImageData(items.get(position));
        holder.setOnItemClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
