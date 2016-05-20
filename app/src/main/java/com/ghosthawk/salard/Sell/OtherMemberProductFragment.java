package com.ghosthawk.salard.Sell;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.GridView;

import com.ghosthawk.salard.Data.Product;
import com.ghosthawk.salard.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtherMemberProductFragment extends Fragment {

    GridView gridView;
    GridProductAdapter mAdapter;

    public OtherMemberProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_other_member_product, container, false);
        gridView = (GridView)view.findViewById(R.id.grid_product);
        mAdapter = new GridProductAdapter();
        gridView.setAdapter(mAdapter);
        initData();




        return view;
    }

    private void initData() {
        for(int i=0; i<10; i++) {
            Product product = new Product();
            product.product_Picture = i;
            if(i % 2 ==0){
                product.product_Soldout = true;
            }
            else
                product.product_Soldout = false;
            mAdapter.add(product);
        }

    }

}
