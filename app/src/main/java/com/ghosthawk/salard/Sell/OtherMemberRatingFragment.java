package com.ghosthawk.salard.Sell;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ghosthawk.salard.Data.CommentResult;
import com.ghosthawk.salard.Manager.NetworkManager;
import com.ghosthawk.salard.R;

import java.io.IOException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtherMemberRatingFragment extends Fragment {
    CommentsAdapter mAdapter;
    RecyclerView listView;
    RecyclerView.LayoutManager mLayoutManager;
    public static final String EXTRA_PERSON_ID = "person_id";
    String person_id;
    public OtherMemberRatingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_member_rating, container, false);
        listView = (RecyclerView)view.findViewById(R.id.rv_list);
        Bundle b = getArguments();
        person_id = b.getString(EXTRA_PERSON_ID);
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
        NetworkManager.getInstance().getProfileComment(getContext(), person_id, new NetworkManager.OnResultListener<CommentResult>() {
            @Override
            public void onSuccess(Request request, CommentResult result) {
                mAdapter.clear();
                mAdapter.addAll(result.comments);
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }
}
