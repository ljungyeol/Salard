package com.ghosthawk.salard.Sell;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ghosthawk.salard.Data.Member;
import com.ghosthawk.salard.Data.notify;
import com.ghosthawk.salard.Data.notifyResult;
import com.ghosthawk.salard.Data.notifynofity;
import com.ghosthawk.salard.GCM.NotifyAdapter;
import com.ghosthawk.salard.GCM.NotifyViewHolder;
import com.ghosthawk.salard.Manager.NetworkManager;
import com.ghosthawk.salard.Manager.PropertyManager;
import com.ghosthawk.salard.Map.MapActivity;
import com.ghosthawk.salard.Message.MessageFragment;
import com.ghosthawk.salard.Other.OtherMemberInfoActivity;
import com.ghosthawk.salard.R;

import java.io.IOException;

import okhttp3.Request;

public class SellHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public String my_id;


    int img[]={
            R.drawable.rank0, R.drawable.rank1, R.drawable.rank2,
            R.drawable.rank3,R.drawable.rank4,R.drawable.rank5
    };
    RecyclerView recyclerView;
    NotifyAdapter mAdapter;
    ImageView imageMem,imageRank;
    TextView textView,textName;
    LinearLayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_home);

        recyclerView = (RecyclerView)findViewById(R.id.rv_list2);
        my_id = PropertyManager.getInstance().getId();

        mAdapter = new NotifyAdapter();
        recyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter.setOnItemClickListener(new NotifyViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, notify noti) {
                String a = noti._id+"";
                NetworkManager.getInstance().getNotify(this, a, new NetworkManager.OnResultListener<notifynofity>() {
                    @Override
                    public void onSuccess(Request request, notifynofity result) {

                        switch(result.notify.getNotify_state()){
                            case 0: {
                                startActivity(new Intent(SellHomeActivity.this, HomeFollowerActivity.class));
                                break;
                            }
                            case 1: {
                                Intent intent = new Intent(SellHomeActivity.this, OtherMemberInfoActivity.class);
                                intent.putExtra(OtherMemberInfoActivity.EXTRA_PERSON_ID,result.notify.getNotify_sendid());
                                startActivity(intent);
                                break;
                            }
                            case 2:{
                                Intent intent = new Intent(SellHomeActivity.this, OtherMemberInfoActivity.class);
                                intent.putExtra(OtherMemberInfoActivity.EXTRA_PERSON_ID,result.notify.getNotify_sendid());
                                startActivity(intent);
                                break;
                            }
                            case 3: {
                                SellHomeFragment ft = new SellHomeFragment();
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.container, ft)
                                        .commit();
                                break;
                            }
                            case 4:{
                                MessageFragment ft = new MessageFragment();
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.container, ft)
                                        .commit();
                                break;
                            }
                        }

                        recyclerView.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFail(Request request, IOException exception) {

                    }
                });
            }
        });
//        textView = (TextView)findViewById(R.id.text_title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Salard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);
//        textView.setText("마이 샐러드");
        Member member = PropertyManager.getInstance().getMember();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemTextAppearance(R.style.SalardNavi);
//        navigationView.setItemTextColor(R.color.colorPrimary);

        Menu menu = navigationView.getMenu();
        MenuItem item =menu.getItem(0);

        View headerView = navigationView.getHeaderView(0);
        imageMem = (ImageView)headerView.findViewById(R.id.img_mem);
        imageRank = (ImageView)headerView.findViewById(R.id.img_rank) ;
        textName = (TextView)headerView.findViewById(R.id.text_name);
        textView = (TextView)headerView.findViewById(R.id.text_noti);
        Glide.with(imageMem.getContext()).load(member.getMem_Picture()).into(imageMem);
        textName.setText(member.getMem_Name());
        SetRank(member);


        Button btn = (Button)headerView.findViewById(R.id.btn_message);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageFragment ft = new MessageFragment();

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, ft)
                        .commit();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SellHomeFragment ft = new SellHomeFragment();

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, ft)
                        .commit();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new SalardMainFragment())
                    .commit();
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sell_home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==R.id.home){

        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {

            startActivity(new Intent(this, MapActivity.class));


            return true;
        }
        if(id==R.id.action_notification){
            if(recyclerView.getVisibility()==View.INVISIBLE) {
                recyclerView.setVisibility(View.VISIBLE);
                NetworkManager.getInstance().getShowNotify(this, PropertyManager.getInstance().getId(), new NetworkManager.OnResultListener<notifyResult>() {
                    @Override
                    public void onSuccess(Request request, notifyResult result) {
                        mAdapter.clear();
                        mAdapter.addAll(result.notify);

                    }

                    @Override
                    public void onFail(Request request, IOException exception) {

                    }
                });


            }else
            mAdapter.clear();
            recyclerView.setVisibility(View.INVISIBLE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //구매자 쪽
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new SalardMainFragment())
                    .commit();



        } else if (id == R.id.nav_wish_list) {
            WishListFragment ft = new WishListFragment();
            Bundle b = new Bundle();
            b.putString(ft.EXTRA_MY_ID,my_id);
            ft.setArguments(b);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ft)
                    .commit();
        }

        //else if (id == R.id.nav_message) {
       //     getSupportFragmentManager().beginTransaction()
        //            .replace(R.id.container, new MessageFragment())
        //            .commit();
       // }

        //판매자 쪽
        else if (id == R.id.nav_add) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new AddProductFragment())
                    .commit();
        } else if (id == R.id.nav_add_list) {
            AddListFragment ft = new AddListFragment();
            Bundle b = new Bundle();
            b.putString(ft.EXTRA_MY_ID,my_id);
            ft.setArguments(b);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ft)
                    .commit();
        } else if (id == R.id.nav_setting) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new SellProfileSettingFragment())
                    .commit();


        }







        //else if (id ==R.id.nav_switch){
           // startActivity(new Intent(SellHomeActivity.this,BuyHomeActivity.class));

        //}



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void SetRank(Member member){
        int i = member.getMem_sellcount();
        if(i==0){

        }
        else if(i>0 && i<10){
            Glide.with(imageRank.getContext()).load(img[0]).into(imageRank);
        }
        else if(i<30){
            Glide.with(imageRank.getContext()).load(img[1]).into(imageRank);
        }
        else if(i<50){
            Glide.with(imageRank.getContext()).load(img[2]).into(imageRank);
        }
        else if(i<80){
            Glide.with(imageRank.getContext()).load(img[3]).into(imageRank);
        }
        else if(i<100){
            Glide.with(imageRank.getContext()).load(img[4]).into(imageRank);
        }
        else if(i>=100){
            Glide.with(imageRank.getContext()).load(img[5]).into(imageRank);
        }


    }


}
