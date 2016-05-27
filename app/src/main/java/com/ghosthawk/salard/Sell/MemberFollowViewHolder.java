package com.ghosthawk.salard.Sell;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ghosthawk.salard.Data.Member;
import com.ghosthawk.salard.R;

import org.w3c.dom.Text;

/**
 * Created by Tacademy on 2016-05-16.
 */
public class MemberFollowViewHolder extends RecyclerView.ViewHolder {
    ImageView thumbView;
    TextView nameView, stateView;
    Member member;
    public interface OnItemClickListener{
        public void onItemClick(View view, Member member);
    }

    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }



    public MemberFollowViewHolder(View itemView) {
        super(itemView);
        thumbView = (ImageView)itemView.findViewById(R.id.image_thumb);
        nameView = (TextView)itemView.findViewById(R.id.text_name);
        stateView = (TextView)itemView.findViewById(R.id.text_state);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    mListener.onItemClick(v,member);
                }

            }
        });
    }


    public void setMember(Member member) {
        this.member = member;

        Glide.with(thumbView.getContext()).load(member.getMem_Picture()).into(thumbView);
        nameView.setText(member.getMem_Name());
        stateView.setText(member.getMem_StatMsg());
    }
}
