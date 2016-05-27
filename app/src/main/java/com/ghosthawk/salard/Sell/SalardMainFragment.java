package com.ghosthawk.salard.Sell;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ghosthawk.salard.Data.PackageProduct;
import com.ghosthawk.salard.Manager.NetworkManager;
import com.ghosthawk.salard.Map.MapActivity;
import com.ghosthawk.salard.R;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalardMainFragment extends Fragment {
    String my_id ="test";

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

    public SalardMainFragment() {
        // Required empty public constructor
    }
    RecyclerView listView;
    ProductAdapter mAdapter;

    LinearLayoutManager mLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ProductAdapter();
        mAdapter.setOnItemClickListener(new ProductViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, PackageProduct pack) {
                Intent intent = new Intent(getContext(), ProductDetailActivity.class);
                //intent.putExtra("ff",pack);
                intent.putExtra(ProductDetailActivity.EXTRA_ID,pack.get_id());
                intent.putExtra(ProductDetailActivity.EXTRA_MY_ID,my_id);
                /*intent.putExtra(ProductDetailActivity.EXTRA_Name, product.getProduct_Name());
                intent.putExtra(ProductDetailActivity.EXTRA_Count, product.getProduct_Count());
                intent.putExtra(ProductDetailActivity.EXTRA_Price, product.getProduct_Price());
                intent.putExtra(ProductDetailActivity.EXTRA_Picture, product.getProduct_Picture());
                intent.putExtra(ProductDetailActivity.EXTRA_Detail, product.getProduct_Detail());
                intent.putExtra(ProductDetailActivity.EXTRA_Detail2, product.getProduct_Detail2());
                intent.putExtra(ProductDetailActivity.EXTRA_Recipe, product.getProduct_Recipe());*/

                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_salard_main, container, false);
        listView = (RecyclerView)view.findViewById(R.id.rv_list);
        listView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(mLayoutManager);
        Button btn = (Button)view.findViewById(R.id.btn_map);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MapActivity.class));
            }
        });
        init();




        return view;
    }


    public void onResume(){
        super.onResume();
        init();
    }
    int img[] = {
            R.drawable.sample1, R.drawable.sample2, R.drawable.sample3,
            R.drawable.sample4, R.drawable.sample5, R.drawable.sample6,
            R.drawable.sample7, R.drawable.sample8, R.drawable.sample9,
            R.drawable.sample10};

    private void init() {
        String xloca = "100";
        String yloca = "200";
        NetworkManager.getInstance().getHomeProductList(getContext(), xloca, yloca, my_id, new NetworkManager.OnResultListener<List<PackageProduct>>() {
                    @Override
                    public void onSuccess(Request request, List<PackageProduct> result) {
                        mHandler.sendMessage(mHandler.obtainMessage(1, result));
                        //mAdapter.clear();
                        //mAdapter.addAll(result);


                    }

                    @Override
                    public void onFail(Request request, IOException exception) {
                        Toast.makeText(getContext(),"불러올 수 없습니다.",Toast.LENGTH_SHORT).show();
                    }
                });

/*
        mAdapter.clear();
        for(int i =0; i<10; i++){
            Product product = new Product();
            product.setProduct_Name("음식"+i);
            product.setProduct_Price(i*1000);
            product.setProduct_Count(i);
            product.setProduct_Picture(img[i]);
            product.setProduct_Detail("닭가슴살 100g, 발사믹 소스 1개, 달걀"+i+"개");
            product.setProduct_Detail2("소금, 후추, 간장, 물"+i+"5컵");
            product.setProduct_Recipe("1. 레시피를 본다. " + "\n2. 그대로 만든다." + "\n3. 먹는다.");
            if(i % 2 ==0){
                product.product_Soldout = true;
            }
            else
                product.product_Soldout = false;
            product.setProduct_Mem_Id(i);
            mAdapter.add(product);

        }*/
    }

}
