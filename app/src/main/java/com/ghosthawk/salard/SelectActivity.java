package com.ghosthawk.salard;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ghosthawk.salard.Sell.SellHomeActivity;
import com.ghosthawk.salard.Sell.SellHomeDialogFragment;

public class SelectActivity extends AppCompatActivity {
    ImageView imageSell,imageBuy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        imageSell = (ImageView)findViewById(R.id.img_sell);
        imageBuy = (ImageView)findViewById(R.id.img_buy);
        imageSell.setImageResource(R.drawable.sample2);
        imageBuy.setImageResource(R.drawable.sample3);


        imageSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SellHomeDialogFragment f = new SellHomeDialogFragment();
                f.show(getSupportFragmentManager(),"dialog");


                //startActivity(new Intent(SelectActivity.this,SellHomeActivity.class));
            }
        });




    }
}
