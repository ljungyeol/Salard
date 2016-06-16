package com.ghosthawk.salard.Message;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ghosthawk.salard.Data.Message;
import com.ghosthawk.salard.Data.MessageResult;
import com.ghosthawk.salard.Manager.DataConstant;
import com.ghosthawk.salard.Manager.DataManager;
import com.ghosthawk.salard.Manager.NetworkManager;
import com.ghosthawk.salard.Manager.PropertyManager;
import com.ghosthawk.salard.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {
        public static final String EXTRA_MY_ID = "my_id";
    String my_id;
    RecyclerView listView;
    MessageAdapter mAdapter;
    List<Message> messages = new ArrayList<>();
//    SimpleCursorAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    public MessageFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        my_id = PropertyManager.getInstance().getId();
        listView = (RecyclerView)view.findViewById(R.id.rv_list);
        mAdapter = new MessageAdapter();
        listView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(mLayoutManager);
        mAdapter.setOnItemClickListener(new MessageViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Message message) {
                Intent intent = new Intent(getContext(), ChattingActivity.class);
                intent.putExtra(ChattingActivity.EXTRA_MESSAGE, message);
                startActivity(intent);

            }
        });
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        messages = DataManager.getInstance().getChatUserList();
        mAdapter.clear();
        mAdapter.addAll(messages);
//        init();
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
