package com.ghosthawk.salard.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ghosthawk.salard.Data.Member;
import com.ghosthawk.salard.Data.MyResult;
import com.ghosthawk.salard.Data.SuccessCode;
import com.ghosthawk.salard.MainActivity;
import com.ghosthawk.salard.Manager.NetworkManager;
import com.ghosthawk.salard.Manager.PropertyManager;
import com.ghosthawk.salard.R;
import com.ghosthawk.salard.Sell.SellHomeActivity;

import java.io.IOException;

import okhttp3.Request;

public class LoginActivity extends AppCompatActivity {
    EditText editName,editPassword;
    String id,pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btn=(Button)findViewById(R.id.btn_login);
        editName = (EditText)findViewById(R.id.edit_id);
        editPassword = (EditText)findViewById(R.id.edit_pw);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = editName.getText().toString();
                pwd = editPassword.getText().toString();

                if(TextUtils.isEmpty(id) ||  TextUtils.isEmpty(pwd)){
                    Toast.makeText(LoginActivity.this,"빈 곳이 있습니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
                NetworkManager.getInstance().getLogin(this, id, pwd, new NetworkManager.OnResultListener<MyResult>() {
                    @Override
                    public void onSuccess(Request request, MyResult result) {
                        if(result.getSuccess_code()==1){
                            Toast.makeText(LoginActivity.this,"로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                            //--TODO 나중에는 putExtra없애면된다.
                            PropertyManager.getInstance().setLogin(true);
                            PropertyManager.getInstance().setPassword(pwd);
                            PropertyManager.getInstance().setId(id);
                            PropertyManager.getInstance().setMember(result.member);
                            Toast.makeText(LoginActivity.this,result.member.getMem_Name(),Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            i.putExtra(MainActivity.EXTRA_INDEX,"main");
                            startActivity(i);}
                        else
                            Toast.makeText(LoginActivity.this,"아이디 또는 비밀번호가 틀렸습니다.",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(Request request, IOException exception) {
                        Toast.makeText(LoginActivity.this,"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        btn = (Button)findViewById(R.id.btn_email);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, MembershipActivity.class);
                startActivity(i);
            }
        });


    }
}
