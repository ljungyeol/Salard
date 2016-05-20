package com.ghosthawk.salard.Sell;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ghosthawk.salard.Data.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-05-20.
 */
public class GridWishListAdapter extends BaseAdapter {
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
