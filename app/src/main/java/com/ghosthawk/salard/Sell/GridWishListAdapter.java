package com.ghosthawk.salard.Sell;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ghosthawk.salard.Data.PackageProduct;
import com.ghosthawk.salard.Data.Product;
import com.ghosthawk.salard.Message.MessageViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-05-20.
 */
public class GridWishListAdapter extends BaseAdapter {
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

    GridWishListItemView.OnItemClickListener mListener;
    public void setOnItemClickListener(GridWishListItemView.OnItemClickListener listener){
        mListener = listener;
    }


    public void onBindView(GridWishListItemView holder, int position) {
        holder.setImageData(items.get(position));
        holder.setOnItemClickListener(mListener);
    }



    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridWishListItemView view;

        if(convertView==null){
            view = new GridWishListItemView(parent.getContext());
        } else {
            view = (GridWishListItemView)convertView;
        }
        view.setImageData(items.get(position));
        return view;
    }
}
