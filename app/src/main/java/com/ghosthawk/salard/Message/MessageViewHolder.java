package com.ghosthawk.salard.Message;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ghosthawk.salard.Data.Comments;
import com.ghosthawk.salard.Data.Member;
import com.ghosthawk.salard.Data.Message;
import com.ghosthawk.salard.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-05-25.
 */
public class MessageViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView,imageRank;
    TextView textName,textStatmsg,textDate,textNoti;

    Message message;
    public interface OnItemClickListener{
        public void onItemClick(View view, Message message);
    }
    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public MessageViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView)itemView.findViewById(R.id.img_mem);
        imageRank = (ImageView)itemView.findViewById(R.id.img_rank);
        textName = (TextView)itemView.findViewById(R.id.text_name);
        textStatmsg = (TextView)itemView.findViewById(R.id.text_statmsg);
        textDate = (TextView)itemView.findViewById(R.id.text_date);
        textNoti = (TextView)itemView.findViewById(R.id.text_noti);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    mListener.onItemClick(v,message);
                }

            }
        });
    }
    int imgg[] = {
            R.drawable.sample1, R.drawable.sample2, R.drawable.sample3, R.drawable.sample4,
            R.drawable.sample5, R.drawable.sample6
    };
    int img[] = {
            R.drawable.rank0,R.drawable.rank1,R.drawable.rank2, R.drawable.rank3,R.drawable.rank4,R.drawable.rank5
    };

    public void setMessage(Message message) {
        this.message = message;

        //사람사진
        Glide.with(imageView.getContext()).load(message.member.getMem_Picture()).into(imageView);
        textDate.setText(message.msg_date);
        textName.setText(message.member.getMem_Name());
        //상대 닉네임
        textStatmsg.setText(message.getMsg_content());
        //음식을 추가하였습니다.
        //등급
        int i = message.member.getMem_sellcount();
        if(i==0){

        }
        else if(i>0 && i<10){
            Glide.with(imageRank.getContext()).load(img[0]).into(imageRank);
        }
        else if(i<30){
            Glide.with(imageRank.getContext()).load(img[1]).into(imageRank);
        }
        else if(i<50){
            Glide.with(imageRank.getContext()).load(img[2]).into(imageRank);
        }
        else if(i<80){
            Glide.with(imageRank.getContext()).load(img[3]).into(imageRank);
        }
        else if(i<100){
            Glide.with(imageRank.getContext()).load(img[4]).into(imageRank);
        }
        else if(i>=100){
            Glide.with(imageRank.getContext()).load(img[5]).into(imageRank);
        }
        else{

        }

    }



}
