package com.ghosthawk.salard.Common;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ghosthawk.salard.Buy.BuyProfileSettingFragment;
import com.ghosthawk.salard.MainActivity;
import com.ghosthawk.salard.R;
import com.ghosthawk.salard.SelectActivity;

public class ReIntroActivity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_intro);
        imageView = (ImageView)findViewById(R.id.img_tutorial);
        imageView.setImageResource(R.drawable.sample1);

    }
}
