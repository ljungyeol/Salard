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
    ImageView imageMem,imageRank;
    TextView nameView;
    Member member;
    public interface OnItemClickListener{
        public void onItemClick(View view, Member member);
    }

    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    //--TODO 랭크 받아서 해야된다.
    //-TODO 받는곳에서 상태메세지 없애고 아이디 대신에 이름 받아야한다
    public MemberFollowViewHolder(View itemView) {
        super(itemView);
        imageMem = (ImageView)itemView.findViewById(R.id.img_mem);
        imageRank = (ImageView)itemView.findViewById(R.id.img_rank);
        nameView = (TextView)itemView.findViewById(R.id.text_name);
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

        Glide.with(imageMem.getContext()).load(member.getMem_Picture()).into(imageMem);
        nameView.setText(member.getMem_Name());
    }
}
