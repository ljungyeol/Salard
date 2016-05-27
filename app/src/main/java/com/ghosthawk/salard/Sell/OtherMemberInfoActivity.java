package com.ghosthawk.salard.Sell;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabWidget;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ghosthawk.salard.Data.Comments;
import com.ghosthawk.salard.Data.Member;
import com.ghosthawk.salard.Data.OtherProfileResult;
import com.ghosthawk.salard.Data.PackageProduct;
import com.ghosthawk.salard.Data.ProfileResult;
import com.ghosthawk.salard.Data.SuccessCode;
import com.ghosthawk.salard.Manager.NetworkManager;
import com.ghosthawk.salard.R;

import java.io.IOException;
import java.util.List;
import java.util.zip.Inflater;

import okhttp3.Request;

public class OtherMemberInfoActivity extends AppCompatActivity {
    public static final String EXTRA_Name = "name";
    public static final String EXTRA_Statmsg = "Statmsg";
    public static final String EXTRA_Picture = "Pirture";
    public static final String EXTRA_Follow = "Follow";
    public static final String EXTRA_Location = "Location";
    public static final String EXTRA_MY_ID = "my_id";
    public static final String EXTRA_PERSON_ID = "person_id";
    TextView textMemName,textMemStatmsg,textMemLocation,textMemFollow;
    ImageView imageView2;
    String my_id, person_id;
    FragmentTabHost tabHost;
    boolean key = true;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_member_info);
        my_id = getIntent().getStringExtra(EXTRA_MY_ID);
        person_id= getIntent().getStringExtra(EXTRA_PERSON_ID);

        tabHost = (FragmentTabHost)findViewById(R.id.rv_list);
        tabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);

        TabWidget tabWidget = (TabWidget)findViewById(android.R.id.tabs);

        View TabHeader = LayoutInflater.from(this).inflate(R.layout.tab_header,tabWidget,false);
        TextView textView = (TextView)TabHeader.findViewById(R.id.text_title);

        Bundle b = new Bundle();
        b.putString(OtherMemberProductFragment.EXTRA_PERSON_ID,person_id);
        textView.setText(R.string.other_tab_product_title);
        tabHost.addTab(tabHost.newTabSpec("a").setIndicator(TabHeader),OtherMemberProductFragment.class,b);

        TabHeader = LayoutInflater.from(this).inflate(R.layout.tab_header,tabWidget,false);
        textView = (TextView)TabHeader.findViewById(R.id.text_title);
        Bundle c = new Bundle();
        c.putString(OtherMemberRatingFragment.EXTRA_PERSON_ID,person_id);
        textView.setText(R.string.other_tab_rating_title);
        tabHost.addTab(tabHost.newTabSpec("b").setIndicator(TabHeader),OtherMemberRatingFragment.class,c);


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

        btn = (Button)findViewById(R.id.btn_follow);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(key){
                    NetworkManager.getInstance().getFollow(this, my_id, person_id, new NetworkManager.OnResultListener<SuccessCode>() {
                        @Override
                        public void onSuccess(Request request, SuccessCode result) {
                            btn.setText("넌 이미 친구해있다");
                            key=false;

                        }

                        @Override
                        public void onFail(Request request, IOException exception) {

                        }
                    });
                }
                else {
                    NetworkManager.getInstance().getUnFollow(this, my_id, person_id, new NetworkManager.OnResultListener<SuccessCode>() {
                        @Override
                        public void onSuccess(Request request, SuccessCode result) {
                            btn.setText("팔로우");
                            key = true;
                        }

                        @Override
                        public void onFail(Request request, IOException exception) {

                        }
                    });
                }
            }
        });







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


    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        NetworkManager.getInstance().getProfile(this, person_id,my_id, new NetworkManager.OnResultListener<OtherProfileResult>() {
            @Override
            public void onSuccess(Request request, OtherProfileResult result) {
                textMemName.setText(result.member.getMem_Name());
                textMemStatmsg.setText(result.member.getMem_StatMsg());
                textMemLocation.setText(result.member.getMem_Location());
                textMemFollow.setText(result.member.getMem_followercount()+"");
                Glide.with(imageView2.getContext()).load(result.member.getMem_Picture()).into(imageView2);
                for(int i=0;i<result.following.size();i++){
                    if (result.member.mem_id.equals(result.following.get(i).follower_id)){
                        key=false;
                        break;
                    }
                }
                if(!key){
                    //btn.setEnabled(false);
                    btn.setText("넌 이미 친구했다");
                }
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });

    }
}
