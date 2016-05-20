package com.ghosthawk.salard.Sell;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ghosthawk.salard.Common.VersionActivity;
import com.ghosthawk.salard.Data.Product;
import com.ghosthawk.salard.Map.MapActivity;
import com.ghosthawk.salard.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalardMainFragment extends Fragment {

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
            public void onItemClick(View view, Product product) {
                Intent intent = new Intent(getContext(), ProductDetailActivity.class);
                intent.putExtra("ff",product);
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
    int img[] = {
            R.drawable.sample1, R.drawable.sample2, R.drawable.sample3,
            R.drawable.sample4, R.drawable.sample5, R.drawable.sample6,
            R.drawable.sample7, R.drawable.sample8, R.drawable.sample9,
            R.drawable.sample10};

    private void init() {
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

        }
    }

}
