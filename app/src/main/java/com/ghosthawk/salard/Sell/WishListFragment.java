package com.ghosthawk.salard.Sell;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.ghosthawk.salard.Data.WishList;
import com.ghosthawk.salard.Manager.NetworkManager;
import com.ghosthawk.salard.R;

import java.io.IOException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishListFragment extends Fragment {
    public static final String EXTRA_MY_ID = "my_id";
    String my_id;

    RecyclerView gridView;
    GridListAdapter mAdapter;
    GridLayoutManager mLayoutManager;
    public WishListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wish_list, container, false);
        gridView = (RecyclerView) view.findViewById(R.id.rv_list);
        gridView.setAdapter(mAdapter);
        mLayoutManager = new GridLayoutManager(getContext(),2);
        gridView.setLayoutManager(mLayoutManager);
        Bundle b = getArguments();
        my_id = b.getString(EXTRA_MY_ID);



        //initData();




        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        NetworkManager.getInstance().getWishList(getContext(), my_id, new NetworkManager.OnResultListener<WishList>() {
            @Override
            public void onSuccess(Request request, WishList result) {
                mAdapter.clear();
                mAdapter.addAll(result.packageproduct);
            }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(getContext(),"불러올 수 없습니다.",Toast.LENGTH_SHORT).show();
            }
        });


        /*
        for(int i=0; i<10; i++) {
            Product product = new Product();
            product.product_Picture = i;
            if(i % 2 ==0){
                product.product_Soldout = true;
            }
            else
                product.product_Soldout = false;
            mAdapter.add(product);
        }*/

    }

}
