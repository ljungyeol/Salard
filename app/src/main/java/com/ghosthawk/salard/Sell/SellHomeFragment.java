package com.ghosthawk.salard.Sell;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.ghosthawk.salard.Data.MyPageResult;
import com.ghosthawk.salard.Data.Rating;
import com.ghosthawk.salard.Manager.NetworkManager;
import com.ghosthawk.salard.Manager.PropertyManager;
import com.ghosthawk.salard.Other.CommentsAdapter;
import com.ghosthawk.salard.R;

import java.io.IOException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class SellHomeFragment extends Fragment {
    public static final String EXTRA_MY_ID = "my_id";
    RecyclerView listView;
    CommentsAdapter mAdapter;
    String my_id;

    LinearLayoutManager mLayoutManager;
    TextView textFollowing, textFollower,textName,textStat,textLoca,textComment,textnono, text1,text2;
    ImageView imageView,imageView2,imageRank;
    Button btnRating;
    public SellHomeFragment() {
        // Required empty public constructor
    }
    //!--TODO 랭크 설정해야된다

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sell_home, container, false);
        my_id = PropertyManager.getInstance().getId();
        btnRating = (Button)view.findViewById(R.id.btn_rating);
        textnono = (TextView)view.findViewById(R.id.text_noti);
        imageView2=(ImageView)view.findViewById(R.id.img_modify) ;
        imageView = (ImageView)view.findViewById(R.id.img_my);
        imageRank = (ImageView)view.findViewById(R.id.img_rank);
        textName = (TextView)view.findViewById(R.id.text_name);
        textStat = (TextView)view.findViewById(R.id.text_statmsg);
        textLoca = (TextView)view.findViewById(R.id.text_location);
        textComment = (TextView)view.findViewById(R.id.text_comment) ;
        text1 = (TextView)view.findViewById(R.id.text1);
        text2 = (TextView)view.findViewById(R.id.text2);
        textFollower = (TextView)view.findViewById(R.id.text_follower);
        textFollower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),HomeFollowerActivity.class);
                i.putExtra(HomeFollowerActivity.EXTRA_MY_ID,my_id);
                startActivity(i);
            }
        });

        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),HomeFollowerActivity.class);
                i.putExtra(HomeFollowerActivity.EXTRA_MY_ID,my_id);
                startActivity(i);
            }
        });
        textFollowing = (TextView)view.findViewById(R.id.text_following);
        textFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),HomeFollowingActivity.class);
                i.putExtra(HomeFollowingActivity.EXTRA_MY_ID,my_id);
                startActivity(i);

            }
        });
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),HomeFollowingActivity.class);
                i.putExtra(HomeFollowingActivity.EXTRA_MY_ID,my_id);
                startActivity(i);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),SellHomeModifyActivity.class);
                intent.putExtra(SellHomeModifyActivity.EXTRA_MY_ID,my_id);
                startActivity(intent);
            }
        });

        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listView.getVisibility()==View.GONE){
                    listView.setVisibility(View.VISIBLE);
                    textComment.setText("후기 접기");
                    btnRating.setPressed(true);
                }
                else{
                    listView.setVisibility(View.GONE);
                    textComment.setText("후기 더보기");
                    btnRating.setPressed(false);
                }
            }
        });


        listView = (RecyclerView)view.findViewById(R.id.rv_list);
        mAdapter = new CommentsAdapter();
        listView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(mLayoutManager);



        return view;

    }
    public void onResume(){
        super.onResume();
        init();
    }



    private void init() {
        NetworkManager.getInstance().getMyPage(getContext(), my_id, new NetworkManager.OnResultListener<MyPageResult>() {
            @Override
            public void onSuccess(Request request, MyPageResult result) {
                Glide.with(imageView.getContext()).load(result.member.getMem_Picture()).into(imageView);
                textName.setText(result.member.getMem_Name());
                textStat.setText(result.member.getMem_StatMsg());
                textLoca.setText(result.member.getMem_locaname());
                textFollowing.setText(result.member.getMem_followingcount()+"");
                textFollower.setText(result.member.getMem_followercount()+"");
                mAdapter.clear();
                mAdapter.addAll(result.comments);


            }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(getContext(),"불러올 수 없습니다.",Toast.LENGTH_SHORT).show();

            }
        });


        /*mAdapter.clear();
        for(int i =0;i<10;i++){
            Rating rating = new Rating();
            rating.setName("Name"+ i);
            rating.setMsg("Tired...."+i);

            mAdapter.add(rating);
        }*/
    }
}
