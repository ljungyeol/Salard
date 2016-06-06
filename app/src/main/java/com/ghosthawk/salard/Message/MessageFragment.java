package com.ghosthawk.salard.Message;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ghosthawk.salard.Data.MessageResult;
import com.ghosthawk.salard.Manager.NetworkManager;
import com.ghosthawk.salard.R;

import java.io.IOException;

import okhttp3.Request;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {
        public static final String EXTRA_MY_ID = "my_id";
    String my_id;
    RecyclerView listView;
    MessageAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        Bundle b = getArguments();
        my_id = b.getString(EXTRA_MY_ID);
        listView = (RecyclerView)view.findViewById(R.id.rv_list);
        mAdapter = new MessageAdapter();
        listView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(mLayoutManager);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        init();
        //NetworkManager.getInstance().getMessage(getContext(),my_id,)
    }

    private void init() {
        NetworkManager.getInstance().getMessage(getContext(), my_id, new NetworkManager.OnResultListener<MessageResult>() {
            @Override
            public void onSuccess(Request request, MessageResult result) {
                mAdapter.clear();
                mAdapter.addAll(result.message);
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }
}
