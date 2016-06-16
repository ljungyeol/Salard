package com.ghosthawk.salard;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.ghosthawk.salard.Data.Member;
import com.ghosthawk.salard.Data.MyResult;
import com.ghosthawk.salard.Data.SuccessCode;
import com.ghosthawk.salard.GCM.RegistrationIntentService;
import com.ghosthawk.salard.Login.LoginActivity;
import com.ghosthawk.salard.Manager.NetworkManager;
import com.ghosthawk.salard.Manager.PropertyManager;
import com.ghosthawk.salard.Sell.SellHomeActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.io.IOException;

import okhttp3.Request;

public class IntroActivity extends AppCompatActivity {
    Handler mHandler = new Handler(Looper.getMainLooper());
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                doRealStart();
            }
        };
        setUpIfNeeded();

    }
    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(RegistrationIntentService.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLAY_SERVICES_RESOLUTION_REQUEST &&
                resultCode == Activity.RESULT_OK) {
            setUpIfNeeded();
        }
    }

    private void setUpIfNeeded() {
        if (checkPlayServices()) {
            String regId = PropertyManager.getInstance().getRegistrationToken();
            if (!regId.equals("")) {
                doRealStart();
            } else {
                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);
            }
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                Dialog dialog = apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                });
                dialog.show();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }
    private void doRealStart(){
        startSplash();
    }

    private void startSplash() {
                String pwd = PropertyManager.getInstance().getPassword();
                String id = PropertyManager.getInstance().getId();
                if(!TextUtils.isEmpty(pwd)){

                    NetworkManager.getInstance().getLogin(this, id, pwd, PropertyManager.getInstance().getRegistrationToken(),new NetworkManager.OnResultListener<MyResult>() {
                        @Override
                        public void onSuccess(Request request, MyResult result) {
                            if(result.getSuccess_code()==1) {
                                Toast.makeText(IntroActivity.this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                PropertyManager.getInstance().setLogin(true);
                                PropertyManager.getInstance().setMember(result.member);
                                goMain();
                            }
                            else {
                                Toast.makeText(IntroActivity.this, "아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                                goLogin();
                            }
                        }

                        @Override
                        public void onFail(Request request, IOException exception) {
                            Toast.makeText(IntroActivity.this,"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                            goLogin();
                        }
                    });
                }

                else{
                    AccessToken token = AccessToken.getCurrentAccessToken();
                    if (token==null){
                        goLogin();
                    }
                    else {
                        NetworkManager.getInstance().getFbLogin(this, token.getToken(), PropertyManager.getInstance().getRegistrationToken(),new NetworkManager.OnResultListener<MyResult>() {
                            @Override
                            public void onSuccess(Request request, MyResult result) {
                                PropertyManager.getInstance().setLogin(true);
                                PropertyManager.getInstance().setMember(result.member);
                                PropertyManager.getInstance().setId(result.member.getMem_id());
                                Toast.makeText(IntroActivity.this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                goMain();
                            }

                            @Override
                            public void onFail(Request request, IOException exception) {
                                Toast.makeText(IntroActivity.this, "error : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                PropertyManager.getInstance().setId("");
                                LoginManager.getInstance().logOut();
                                goLogin();
                            }
                        });
                    }
//                    else {
//                        PropertyManager.getInstance().setId("");
//                        LoginManager.getInstance().logOut();
//                        goLogin();
//                    }

                }

            }


    private void goMain(){
        Intent i = new Intent(IntroActivity.this, SellHomeActivity.class);
        startActivity(i);
        finish();
    }
    private void goLogin(){
        Intent i = new Intent(IntroActivity.this,LoginActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public SharedPreferences getSharedPreferences(String name, int mode) {
        return super.getSharedPreferences(name, mode);
    }
}


