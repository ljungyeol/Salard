package com.ghosthawk.salard;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ghosthawk.salard.Login.LoginActivity;
import com.ghosthawk.salard.Sell.SellHomeActivity;
import com.matthewtamlin.sliding_intro_screen_library.DotIndicator;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_INDEX = "main";

    ViewPager viewPager;
    HomeAdapter mAdapter;
    String index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewPager_home);
        index = getIntent().getStringExtra(EXTRA_INDEX);
        final DotIndicator infoIndicator = (DotIndicator)findViewById(R.id.dot);

        mAdapter = new HomeAdapter(this);
        viewPager.setAdapter(mAdapter);
        infoIndicator.setNumberOfItems(mAdapter.getCount());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                infoIndicator.setSelectedItem(viewPager.getCurrentItem(), true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        Button btn = (Button)findViewById(R.id.btn_skip);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index.equals("main")) {
                    startActivity(new Intent(MainActivity.this, SellHomeActivity.class));
                }
                else
                    finish();
            }
        });


        infoIndicator.setSelectedDotColor(Color.parseColor("#013ADF"));
        infoIndicator.setUnselectedDotColor(Color.parseColor("#CFCFCF"));


    }





}
