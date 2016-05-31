package com.ghosthawk.salard.Message;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ghosthawk.salard.Data.Comments;
import com.ghosthawk.salard.Data.Message;
import com.ghosthawk.salard.R;
import com.ghosthawk.salard.Sell.CommentsViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-05-25.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    List<Message> items = new ArrayList<>();

    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }

    public void add(Message message){
        items.add(message);
        notifyDataSetChanged();
    }

    public void addAll(List<Message> items){
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    MessageViewHolder.OnItemClickListener mListener;
    public void setOnItemClickListener(MessageViewHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_message_list,null);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        holder.setMessage(items.get(position));
        holder.setOnItemClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
