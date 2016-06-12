package com.ghosthawk.salard.Message;


import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.ghosthawk.salard.Data.Rating;
import com.ghosthawk.salard.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RatingFragment extends DialogFragment{
    RatingBar ratingbar;
    EditText editrating;
    Button btnrating;

    public RatingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rating, container, false);
        ratingbar = (RatingBar)view.findViewById(R.id.ratingbar);
        editrating = (EditText)view.findViewById(R.id.edit_rating);
        btnrating = (Button)view.findViewById(R.id.btn_rating);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

//        // remove dialog background
//        getDialog().getWindow().setBackgroundDrawable(
//                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return view;
    }

}
