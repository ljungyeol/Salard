package com.ghosthawk.salard.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ghosthawk.salard.Data.PackageProduct;
import com.ghosthawk.salard.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.Map;

/**
 * Created by Tacademy on 2016-06-01.
 */
public class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    ImageView img_mem, img_main;
    TextView textName,textPrice;
    Map<Marker, PackageProduct> poiResolver;
    View contentView;
    public MyInfoWindowAdapter(Context context, Map<Marker,PackageProduct> poiResolver){
        contentView = LayoutInflater.from(context).inflate(R.layout.view_map_window,null);
        img_mem = (ImageView)contentView.findViewById(R.id.img_mem);
        img_main = (ImageView)contentView.findViewById(R.id.img_main);
        textName = (TextView)contentView.findViewById(R.id.text_name);
        textPrice = (TextView)contentView.findViewById(R.id.text_price);
        this.poiResolver = poiResolver;
    }

    @Override
    public View getInfoWindow(Marker marker) {
         PackageProduct packageProduct = poiResolver.get(marker);
        textName.setText(packageProduct.getPackage_name());
        textPrice.setText(packageProduct.getPackage_price());
        Glide.with(img_mem.getContext()).load(packageProduct.getPackage_personpicture()).into(img_mem);
        Glide.with(img_main.getContext()).load(packageProduct.getPackage_mainpicture()).into(img_main);

        return contentView;
    }

    @Override
    public View getInfoContents(Marker marker) {
        PackageProduct packageProduct = poiResolver.get(marker);
        textName.setText(packageProduct.getPackage_name());
        textPrice.setText(packageProduct.getPackage_price());
        Glide.with(img_mem.getContext()).load(packageProduct.getPackage_personpicture()).into(img_mem);
        Glide.with(img_main.getContext()).load(packageProduct.getPackage_mainpicture()).into(img_main);

        return contentView;
    }
}
