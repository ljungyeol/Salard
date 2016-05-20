package com.ghosthawk.salard.Sell;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ghosthawk.salard.Common.RankActivity;
import com.ghosthawk.salard.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SellHomeDialogFragment extends DialogFragment {

    /*String message;
    public static SellHomeDialogFragment newInstance(String message){
        SellHomeDialogFragment f = new SellHomeDialogFragment();
        Bundle b = new Bundle();
        b.putString("판매자로 로그인 할 것이냐",message);
        f.setArguments(b);
        return f;
    }*/


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if(getArguments()!=null){
            message=getArguments().getString("message");
        }*/
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("판매자로 로그인 하시겠습니까");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getContext(), "확인 Click", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), SellHomeActivity.class));

            }
        });


        builder.setNegativeButton("취소", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getContext(), "취소 Click", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });


        return builder.create();
    }
}