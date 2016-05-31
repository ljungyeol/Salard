package com.ghosthawk.salard.Sell;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ghosthawk.salard.Data.Comments;
import com.ghosthawk.salard.Data.Member;
import com.ghosthawk.salard.Data.Rating;
import com.ghosthawk.salard.R;

import org.w3c.dom.Text;

/**
 * Created by Tacademy on 2016-05-23.
 */
public class CommentsViewHolder extends RecyclerView.ViewHolder {
    TextView textName,textMsg;
    RatingBar ratingBar;

    Comments comments;
    public interface OnItemClickListener{
        public void onItemClick(View view, Comments comments);
    }


    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public CommentsViewHolder(View itemView) {
        super(itemView);
        textName = (TextView)itemView.findViewById(R.id.text_name);
        textMsg = (TextView)itemView.findViewById(R.id.text_msg);
        ratingBar = (RatingBar)itemView.findViewById(R.id.ratingBar2);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    mListener.onItemClick(v,comments);
                }

            }
        });
    }

    public void setComments(Comments comments) {
        this.comments = comments;

        //Glide.with(thumbView.getContext()).load(product.getThumbnailUrl()).into(thumbView);

        textName.setText(comments.getComment_sendid());
        textMsg.setText(comments.getComment_text());
        ratingBar.setRating(comments.getComment_grade());

    }


}
