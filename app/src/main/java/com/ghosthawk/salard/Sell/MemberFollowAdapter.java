package com.ghosthawk.salard.Sell;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ghosthawk.salard.Data.Member;
import com.ghosthawk.salard.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-05-16.
 */
public class MemberFollowAdapter extends RecyclerView.Adapter<MemberFollowViewHolder> {
    List<Member> items = new ArrayList<>();

    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }

    public void add(Member member){
        items.add(member);
        notifyDataSetChanged();
    }

    public void addAll(List<Member> items){
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    MemberFollowViewHolder.OnItemClickListener mListener;
    public void setOnItemClickListener(MemberFollowViewHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public MemberFollowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_follow_member,null);
        return new MemberFollowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MemberFollowViewHolder holder, int position) {
        holder.setMember(items.get(position));
        holder.setOnItemClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
