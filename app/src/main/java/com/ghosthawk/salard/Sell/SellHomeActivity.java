package com.ghosthawk.salard.Sell;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghosthawk.salard.Message.MessageFragment;
import com.ghosthawk.salard.R;
public class SellHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public String my_id = "test";

    ImageView imageView;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_home);
        textView = (TextView)findViewById(R.id.text_title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView.setText("마이 샐러드");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        imageView = (ImageView)headerView.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.sample10);
        Button btn = (Button)headerView.findViewById(R.id.btn_message);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageFragment ft = new MessageFragment();
                Bundle b = new Bundle();
                b.putString(ft.EXTRA_MY_ID,my_id);
                ft.setArguments(b);
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
                Bundle b = new Bundle();
                b.putString(ft.EXTRA_MY_ID,my_id);
                ft.setArguments(b);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {




            return true;
        }
        if(id==R.id.action_notification){
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

}
