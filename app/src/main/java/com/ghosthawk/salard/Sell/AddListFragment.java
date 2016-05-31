package com.ghosthawk.salard.Sell;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.ghosthawk.salard.Data.PackageProduct;
import com.ghosthawk.salard.Data.Product;
import com.ghosthawk.salard.Data.WishList;
import com.ghosthawk.salard.Manager.NetworkManager;
import com.ghosthawk.salard.R;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddListFragment extends Fragment {
    public static final String EXTRA_MY_ID = "my_id";
    RecyclerView gridView;
    GridListAdapter mAdapter;
    GridLayoutManager mLayoutManager;
    String my_id;
    int _id;
    public AddListFragment() {
        // Required empty public constructor
    }
    private List<PackageProduct> result;

    Handler mHandler= new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                result = (List<PackageProduct>) msg.obj;

/*
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;

                String uri = "full addr"+ .package_mainpicture;

                try {
                    result.bm = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);

                } catch (Exception e) {
                }*/


                mAdapter.clear();
                mAdapter.addAll(result);
                mAdapter.notifyDataSetChanged();


            }


        }
    };




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new GridListAdapter();
        mAdapter.setOnItemClickListener(new GridListViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, PackageProduct pack) {
                Intent intent = new Intent(getContext(),MyProductDetailActivity.class);


                intent.putExtra(MyProductDetailActivity.EXTRA_ID,pack.get_id());
                intent.putExtra(MyProductDetailActivity.EXTRA_MY_ID,my_id);
                startActivity(intent);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_list, container, false);
        gridView = (RecyclerView) view.findViewById(R.id.rv_list);
        gridView.setAdapter(mAdapter);
        mLayoutManager = new GridLayoutManager(getContext(),2);
        gridView.setLayoutManager(mLayoutManager);
        Bundle b = getArguments();
        my_id = b.getString(EXTRA_MY_ID);
        initData();




        return view;
    }

    private void initData() {
        NetworkManager.getInstance().getAddList(getContext(), my_id, new NetworkManager.OnResultListener<WishList>() {
            @Override
            public void onSuccess(Request request, WishList result) {
                //mHandler.sendMessage(mHandler.obtainMessage(1, result.packageproduct));

                mAdapter.clear();
                mAdapter.addAll(result.packageproduct);
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });



        /*for(int i=0; i<10; i++) {
            PackageProduct product = new PackageProduct();
            product.package_mainpicture ="http://52.79.156.18:3000/images/member/salard3.jpg";
            if(i % 2 ==0){
                    product.package_state = 1;
            }
            else
                product.package_state = 0;
            mAdapter.add(product);
        }*/

    }

}
