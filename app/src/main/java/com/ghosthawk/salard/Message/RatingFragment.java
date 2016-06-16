package com.ghosthawk.salard.Message;


import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.net.Network;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.ghosthawk.salard.Data.Comments;
import com.ghosthawk.salard.Data.Rating;
import com.ghosthawk.salard.Manager.NetworkManager;
import com.ghosthawk.salard.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RatingFragment extends DialogFragment{
    RatingBar ratingbar;
    EditText editrating;
    Button btnrating;
    Comments comments = new Comments();
    public RatingFragment() {
        // Required empty public constructor
    }

    public interface OnRatingResultCallback {
        public void OnRatingResult(Comments comments);
    }
    OnRatingResultCallback mListener;
    public void setOnRatingResultCallback(OnRatingResultCallback callback) {
        mListener = callback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rating, container, false);
        ratingbar = (RatingBar)view.findViewById(R.id.ratingbar);
        ratingbar.setRating(5);
        editrating = (EditText)view.findViewById(R.id.edit_rating);
        btnrating = (Button)view.findViewById(R.id.btn_rating);
        btnrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = editrating.getText().toString();
                float rating = ratingbar.getRating();
                if(TextUtils.isEmpty(keyword)){
                   if(rating<=1){
                       keyword = "별로에요";
                   }
                    else if(rating<=2){
                       keyword = "그저 그래요";
                   }
                    else if(rating<=3){
                       keyword = "보통이에요";
                   }
                    else if(rating<=4){
                       keyword = "좋아요!";
                   }
                    else
                       keyword = "다음에 또 기대할게요!";
                }
                comments.setComment_grade(rating);
                comments.setComment_text(keyword);
                mListener.OnRatingResult(comments);
                dismiss();

            }
        });
//        // remove dialog background
//        getDialog().getWindow().setBackgroundDrawable(
//                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setLayout(getResources().getDimensionPixelSize(R.dimen.dialog_width), ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
