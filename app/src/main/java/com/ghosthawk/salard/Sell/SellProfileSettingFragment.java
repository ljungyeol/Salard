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

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.ghosthawk.salard.Common.NoticeActivity;
import com.ghosthawk.salard.Common.RankActivity;
import com.ghosthawk.salard.Common.RuleActivity;
import com.ghosthawk.salard.Common.VersionActivity;
import com.ghosthawk.salard.Data.SuccessCode;
import com.ghosthawk.salard.Login.LoginActivity;
import com.ghosthawk.salard.MainActivity;
import com.ghosthawk.salard.Manager.NetworkManager;
import com.ghosthawk.salard.Manager.PropertyManager;
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
//                        PropertyManager.getInstance().setId("");

                        NetworkManager.getInstance().getDeleteMember(this, PropertyManager.getInstance().getId(), new NetworkManager.OnResultListener<SuccessCode>() {
                            @Override
                            public void onSuccess(Request request, SuccessCode result) {
                                PropertyManager.getInstance().setPassword("");
                                PropertyManager.getInstance().setLogin(false);
                                PropertyManager.getInstance().setUser(null);
                                //--TODO 로그인이랑 인트로 등 페북 필요한곳 삭제 -> myapplication에 설치
//                                FacebookSdk.sdkInitialize(getContext());

                                AccessToken token = AccessToken.getCurrentAccessToken();
                                if(token!=null){
                                    LoginManager loginManager = LoginManager.getInstance();
                                    loginManager.logOut();
                                }
                            }

                            @Override
                            public void onFail(Request request, IOException exception) {

                            }
                        });
                        Toast.makeText(getContext(),"삭제되었습니다.",Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getContext(), LoginActivity.class));
                        getActivity().finish();

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

        btn = (Button)view.findViewById(R.id.btn_logout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("로그아웃하시겠습니까");
                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NetworkManager.getInstance().getLogout(new NetworkManager.OnResultListener<SuccessCode>() {
                            @Override
                            public void onSuccess(Request request, SuccessCode result) {

                                PropertyManager.getInstance().setId("");
                                PropertyManager.getInstance().setPassword("");
                                PropertyManager.getInstance().setLogin(false);
                                PropertyManager.getInstance().setUser(null);
                                //--TODO 로그인이랑 인트로 등 페북 필요한곳 삭제 -> myapplication에 설치
//                                FacebookSdk.sdkInitialize(getContext());

                                AccessToken token = AccessToken.getCurrentAccessToken();
                                if(token!=null){
                                    LoginManager loginManager = LoginManager.getInstance();
                                    loginManager.logOut();
                                }
                                Toast.makeText(getContext(),"로그아웃되었습니다.",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getContext(), LoginActivity.class));
                                getActivity().finish();
                            }

                            @Override
                            public void onFail(Request request, IOException exception) {

                            }
                        });

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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
