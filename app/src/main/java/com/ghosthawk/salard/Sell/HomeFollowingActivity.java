package com.ghosthawk.salard.Sell;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.ghosthawk.salard.Data.FollowingResult;
import com.ghosthawk.salard.Data.Member;
import com.ghosthawk.salard.Manager.NetworkManager;
import com.ghosthawk.salard.R;

import java.io.IOException;

import okhttp3.Request;

public class HomeFollowingActivity extends AppCompatActivity {
    RecyclerView listView;
    MemberFollowAdapter mAdapter;
    public static final String EXTRA_MY_ID = "my_id";
    String my_id;

    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_following);
        listView = (RecyclerView)findViewById(R.id.rv_list);
        mAdapter = new MemberFollowAdapter();
        listView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(mLayoutManager);
        my_id = getIntent().getStringExtra(EXTRA_MY_ID);
        init();
    }
    int img[] = {
            R.drawable.sample1, R.drawable.sample2, R.drawable.sample3,
            R.drawable.sample4, R.drawable.sample5, R.drawable.sample6,
            R.drawable.sample7, R.drawable.sample8, R.drawable.sample9,
            R.drawable.sample10};
    private void init() {
        NetworkManager.getInstance().getFollowingList(this, my_id, new NetworkManager.OnResultListener<FollowingResult>() {
            @Override
            public void onSuccess(Request request, FollowingResult result) {
                mAdapter.clear();
                mAdapter.addAll(result.member);
            }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(HomeFollowingActivity.this,"불러올 수 없습니다.",Toast.LENGTH_SHORT).show();

            }
        });

        /*
        mAdapter.clear();
        for(int i =0;i<10;i++){
            Member member = new Member();
            member.setMem_Name("Name"+ i);
            member.setMem_StatMsg("Tired...."+i);
            //member.setMem_Picture(img[i]);
            mAdapter.add(member);
        }*/
    }

}
