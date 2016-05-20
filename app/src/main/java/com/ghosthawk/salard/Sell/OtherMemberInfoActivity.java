package com.ghosthawk.salard.Sell;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabWidget;
import android.widget.TextView;

import com.ghosthawk.salard.R;

import java.util.zip.Inflater;

public class OtherMemberInfoActivity extends AppCompatActivity {
    public static final String EXTRA_Name = "name";
    public static final String EXTRA_Statmsg = "Statmsg";
    public static final String EXTRA_Picture = "Pirture";
    public static final String EXTRA_Follow = "Follow";
    public static final String EXTRA_Location = "Location";
    TextView textMemName,textMemStatmsg,textMemLocation,textMemFollow;
    ImageView imageView2;

    FragmentTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_member_info);

        tabHost = (FragmentTabHost)findViewById(R.id.rv_list);
        tabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);

        TabWidget tabWidget = (TabWidget)findViewById(android.R.id.tabs);

        View TabHeader = LayoutInflater.from(this).inflate(R.layout.tab_header,tabWidget,false);
        TextView textView = (TextView)TabHeader.findViewById(R.id.text_title);
        textView.setText(R.string.other_tab_product_title);
        tabHost.addTab(tabHost.newTabSpec("a").setIndicator(TabHeader),OtherMemberProductFragment.class,null);

        TabHeader = LayoutInflater.from(this).inflate(R.layout.tab_header,tabWidget,false);
        textView = (TextView)TabHeader.findViewById(R.id.text_title);
        textView.setText(R.string.other_tab_rating_title);
        tabHost.addTab(tabHost.newTabSpec("b").setIndicator(TabHeader),OtherMemberRatingFragment.class,null);


        textMemName = (TextView)findViewById(R.id.text_name2);
       // textMemName.setText(getIntent().getStringExtra(EXTRA_Name));
        textMemStatmsg = (TextView)findViewById(R.id.text_statmsg);
        //textMemStatmsg.setText(getIntent().getStringExtra(EXTRA_Statmsg));
        textMemLocation = (TextView)findViewById(R.id.text_location);
        //textMemLocation.setText(getIntent().getStringExtra(EXTRA_Location));
        textMemFollow = (TextView)findViewById(R.id.text_follow);
        //textMemFollow.setText(getIntent().getIntExtra(EXTRA_Follow,0));
        imageView2 = (ImageView)findViewById(R.id.imageView3);
       // int i = getIntent().getIntExtra(EXTRA_Picture, 0);
        //imageView2.setImageResource(i);










       /*
        TabWidget tabWidget = (TabWidget)findViewById(android.R.id.tabs);
        View tabHeader = inflater.inflate(R.layout.tab_header,tabWidget,false);
        TextView titleView = (TextView)tabHeader.findViewById(R.id.text_title);
        titleView.setText(R.string.home_tab_me_title);
        tabHost.addTab(tabHost.newTabSpec("Me").setIndicator(tabHeader),SellHomeMeFragment.class,null);

        tabHeader = inflater.inflate(R.layout.tab_header,tabWidget,false);
        titleView = (TextView)tabHeader.findViewById(R.id.text_title);
        titleView.setText(R.string.home_tab_sell_follower_title);
        tabHost.addTab(tabHost.newTabSpec("Followers").setIndicator(tabHeader),SellHomeFollowFragment.class,null);
*/
    }



}
