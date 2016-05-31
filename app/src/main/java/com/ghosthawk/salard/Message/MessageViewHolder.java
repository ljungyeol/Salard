package com.ghosthawk.salard.Message;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ghosthawk.salard.Data.Comments;
import com.ghosthawk.salard.Data.Message;
import com.ghosthawk.salard.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-05-25.
 */
public class MessageViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView textName,textStatmsg;

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
        imageView = (ImageView)itemView.findViewById(R.id.img_pic);
        textName = (TextView)itemView.findViewById(R.id.text_name);
        textStatmsg = (TextView)itemView.findViewById(R.id.text_statmsg);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    mListener.onItemClick(v,message);
                }

            }
        });
    }

    public void setMessage(Message message) {
        this.message = message;

        Glide.with(imageView.getContext()).load(message.getMsg_partnerpicture()).into(imageView);

        textName.setText(message.getMsg_memid());
        textStatmsg.setText(message.getMsg_content());

    }
}
