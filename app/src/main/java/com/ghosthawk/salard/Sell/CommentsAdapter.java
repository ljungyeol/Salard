package com.ghosthawk.salard.Sell;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ghosthawk.salard.Data.Comments;
import com.ghosthawk.salard.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-05-23.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsViewHolder> {
    List<Comments> items = new ArrayList<>();

    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }

    public void add(Comments comment){
        items.add(comment);
        notifyDataSetChanged();
    }

    public void addAll(List<Comments> items){
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    CommentsViewHolder.OnItemClickListener mListener;
    public void setOnItemClickListener(CommentsViewHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_rating,null);
        return new CommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentsViewHolder holder, int position) {
        holder.setComments(items.get(position));
        holder.setOnItemClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
