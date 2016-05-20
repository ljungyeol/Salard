package com.ghosthawk.salard.Sell;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ghosthawk.salard.Common.NoticeActivity;
import com.ghosthawk.salard.Common.RankActivity;
import com.ghosthawk.salard.Common.ReIntroActivity;
import com.ghosthawk.salard.Common.RuleActivity;
import com.ghosthawk.salard.Common.VersionActivity;
import com.ghosthawk.salard.R;

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
                startActivity(new Intent(getActivity(), ReIntroActivity.class));
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

        return view;


    }

}
