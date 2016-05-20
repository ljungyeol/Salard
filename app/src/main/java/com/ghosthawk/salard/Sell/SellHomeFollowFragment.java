package com.ghosthawk.salard.Sell;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ghosthawk.salard.Data.Member;
import com.ghosthawk.salard.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SellHomeFollowFragment extends Fragment {


    public SellHomeFollowFragment() {
        // Required empty public constructor
    }

    RecyclerView listView;
    MemberFollowAdapter mAdapter;


    LinearLayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sell_home_follow, container, false);
        listView = (RecyclerView)view.findViewById(R.id.rv_list);
        mAdapter = new MemberFollowAdapter();
        listView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(mLayoutManager);

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
        for(int i =0;i<10;i++){
            Member member = new Member();
            member.setMem_Name("Name"+ i);
            member.setMem_StatMsg("Tired...."+i);
            member.setMem_Picture(img[i]);
            mAdapter.add(member);
        }
    }

}
