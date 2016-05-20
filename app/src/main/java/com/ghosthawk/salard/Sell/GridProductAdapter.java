package com.ghosthawk.salard.Sell;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ghosthawk.salard.Data.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-05-20.
 */
public class GridProductAdapter extends BaseAdapter{
    List<Product> items = new ArrayList<>();

    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }

    public void add(Product product){
        items.add(product);
        notifyDataSetChanged();
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
