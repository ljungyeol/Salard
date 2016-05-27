package com.ghosthawk.salard.Sell;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ghosthawk.salard.Data.PackageProduct;
import com.ghosthawk.salard.Data.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-05-20.
 */
public class GridProductAdapter extends BaseAdapter{
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

    GridProductItemView.OnItemClickListener mListener;
    public void setOnItemClickListener( GridProductItemView.OnItemClickListener listener){
        mListener = listener;
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
        GridProductItemView view;

        if(convertView==null){
            view = new GridProductItemView(parent.getContext());
        } else {
            view = (GridProductItemView)convertView;
        }
        view.setImageData(items.get(position));
        return view;
    }
}
