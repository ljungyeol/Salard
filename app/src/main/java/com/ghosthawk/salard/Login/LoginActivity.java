package com.ghosthawk.salard.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ghosthawk.salard.R;
import com.ghosthawk.salard.Sell.SellHomeActivity;

public class LoginActivity extends AppCompatActivity {
    EditText editName,editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btn=(Button)findViewById(R.id.btn_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SellHomeActivity.class);
                startActivity(i);
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
