package com.ghosthawk.salard.Sell;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ghosthawk.salard.Common.NoticeActivity;
import com.ghosthawk.salard.Common.RankActivity;
import com.ghosthawk.salard.Common.RuleActivity;
import com.ghosthawk.salard.Common.VersionActivity;
import com.ghosthawk.salard.Data.SuccessCode;
import com.ghosthawk.salard.Login.LoginActivity;
import com.ghosthawk.salard.MainActivity;
import com.ghosthawk.salard.Manager.NetworkManager;
import com.ghosthawk.salard.R;

import java.io.IOException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class SellProfileSettingFragment extends Fragment {

    public SellProfileSettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sell_profile_setting, container, false);
        Button btn = (Button)view.findViewById(R.id.btn_notice);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NoticeActivity.class));
            }
        });

        btn = (Button)view.findViewById(R.id.btn_version);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), VersionActivity.class));
            }
        });
        btn = (Button)view.findViewById(R.id.btn_intro);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra(MainActivity.EXTRA_INDEX,"setting");
                startActivity(intent);
            }
        });

        btn = (Button)view.findViewById(R.id.btn_rule);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RuleActivity.class));
            }
        });


        btn = (Button)view.findViewById(R.id.btn_rank);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RankActivity.class));
            }
        });


        btn = (Button)view.findViewById(R.id.btn_delete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("정말 삭제하시겠습니까.");
                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        NetworkManager.getInstance().getFoodRemove(this, _id, new NetworkManager.OnResultListener<SuccessCode>() {
//                            @Override
//                            public void onSuccess(Request request, SuccessCode result) {
//                                Toast.makeText(AddProductModifyActivity.this,"삭제되었습니다.",Toast.LENGTH_SHORT).show();
//                                finish();
//                            }
//
//                            @Override
//                            public void onFail(Request request, IOException exception) {
//
//                            }
//                        });
                        Toast.makeText(getContext(),"삭제되었습니다.",Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getContext(), LoginActivity.class));
                    }
                });

                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(),"취소되었습니다.",Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return view;


    }

}
