package com.ghosthawk.salard.Sell;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.GridView;

import com.ghosthawk.salard.Data.Member;
import com.ghosthawk.salard.Data.PackageProduct;
import com.ghosthawk.salard.Data.PackageProductResult;
import com.ghosthawk.salard.Data.Product;
import com.ghosthawk.salard.Manager.NetworkManager;
import com.ghosthawk.salard.R;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtherMemberProductFragment extends Fragment {
    public static final String EXTRA_PERSON_ID = "person_id";
    String person_id;
    //GridView gridView;
    RecyclerView gridView;
    GridLayoutManager mLayoutManager;

    //GridProductAdapter mAdapter;
    OtherMemberProductAdapter mAdapter;
    public OtherMemberProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_other_member_product, container, false);
        Bundle b = getArguments();
        person_id = b.getString(EXTRA_PERSON_ID);
        gridView = (RecyclerView) view.findViewById(R.id.rv_list);
        mAdapter = new OtherMemberProductAdapter();
        gridView.setAdapter(mAdapter);
        mLayoutManager = new GridLayoutManager(getContext(),3);
        gridView.setLayoutManager(mLayoutManager);
        //initData();

        // PackageProduct pack =(PackageProduct) getIntent().getSerializableExtra("ff");



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        NetworkManager.getInstance().getProfilePackage(getContext(), person_id, new NetworkManager.OnResultListener<PackageProductResult>() {
            @Override
            public void onSuccess(Request request, PackageProductResult result) {
                mAdapter.clear();
                mAdapter.addAll(result._package);
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });



    }



}
