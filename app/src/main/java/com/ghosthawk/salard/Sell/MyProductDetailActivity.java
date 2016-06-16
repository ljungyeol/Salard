package com.ghosthawk.salard.Sell;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ghosthawk.salard.Data.ProductResult;
import com.ghosthawk.salard.Data.SuccessCode;
import com.ghosthawk.salard.Manager.NetworkManager;
import com.ghosthawk.salard.Manager.PropertyManager;
import com.ghosthawk.salard.R;

import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Request;

public class MyProductDetailActivity extends AppCompatActivity {
    ImageView imageView,imageView2,imageView3, imageView4,imageLeft,imageRight;
    TextView textName,textCount,textPrice,textDetail,textDetail2,textRecipe;
    TextView textMemName,textMemLocation;
    Button btn;
    ViewPager viewpager;
    ProductDetailPagerAdapter mAdapter;


    public static final String EXTRA_ID="_id";
    public static final String EXTRA_MY_ID="my_id";
    int id;
    String _id;
    String my_id;
    String main_id;
    String person_id;
    boolean key = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_product_detail);
        if (Build.VERSION.SDK_INT >= 21) {   //상태바 색
            getWindow().setStatusBarColor(Color.parseColor("#00d076"));
        }
        id = getIntent().getIntExtra(EXTRA_ID,0);
        _id=String.valueOf(id);
        main_id = getIntent().getStringExtra(EXTRA_MY_ID);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.selector_action_back);
        viewpager = (ViewPager)findViewById(R.id.viewPager_detail);
        mAdapter = new ProductDetailPagerAdapter(this);
        viewpager.setAdapter(mAdapter);

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        textView = (TextView)findViewById(R.id.text_title);
//        textView.setText("식재료 정보");
        toolbar.setTitle("식재료 정보");




        imageRight = (ImageView)findViewById(R.id.image_right);
        imageLeft = (ImageView)findViewById(R.id.image_left);

        imageView = (ImageView)findViewById(R.id.imageView2);
        imageView2 = (ImageView)findViewById(R.id.img_mem);
        imageView3 = (ImageView)findViewById(R.id.img_setting);
        imageView4 = (ImageView)findViewById(R.id.img_rank);
        textName = (TextView)findViewById(R.id.text_name);
        textCount = (TextView)findViewById(R.id.text_count);
        textPrice = (TextView)findViewById(R.id.text_price);
        textDetail = (TextView)findViewById(R.id.text_detail);
        textDetail2 = (TextView)findViewById(R.id.text_detail2);
        textRecipe = (TextView)findViewById(R.id.text_recipe);

        textMemName = (TextView)findViewById(R.id.text_name2);
        textMemLocation = (TextView)findViewById(R.id.text_location);

        btn = (Button)findViewById(R.id.btn_sell);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(key){
                    NetworkManager.getInstance().getSoldout(this, _id, new NetworkManager.OnResultListener<SuccessCode>() {
                        @Override
                        public void onSuccess(Request request, SuccessCode result) {
                            btn.setClickable(true);
                            key=false;
                        }

                        @Override
                        public void onFail(Request request, IOException exception) {
                            Toast.makeText(MyProductDetailActivity.this,"불러올 수 없습니다.",Toast.LENGTH_SHORT).show();

                        }
                    });
                } else{
                    NetworkManager.getInstance().getSoldoutCancel(this, _id, new NetworkManager.OnResultListener<SuccessCode>() {
                        @Override
                        public void onSuccess(Request request, SuccessCode result) {
                            btn.setClickable(false);
                            key=true;
                        }

                        @Override
                        public void onFail(Request request, IOException exception) {
                            Toast.makeText(MyProductDetailActivity.this,"불러올 수 없습니다.",Toast.LENGTH_SHORT).show();

                        }
                    });
                }


            }
        });


        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProductDetailActivity.this,AddProductModifyActivity.class);
                intent.putExtra(AddProductModifyActivity.EXTRA_ID,_id);
                startActivity(intent);
            }
        });

        imageLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = viewpager.getCurrentItem();
                if (i==0){
                    viewpager.setCurrentItem(1,true);
                }
                else{
                    viewpager.setCurrentItem(0,true);
                }
            }
        });
        imageRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = viewpager.getCurrentItem();
                if (i==0){
                    viewpager.setCurrentItem(1,true);
                }
                else{
                    viewpager.setCurrentItem(0,true);
                }
            }
        });

    }



    public void onResume() {
        super.onResume();
            init();
    }

    private void init() {
        NetworkManager.getInstance().getProductDetail(this, _id, main_id, new NetworkManager.OnResultListener<ProductResult>() {
            @Override
            public void onSuccess(Request request, ProductResult result) {
                mAdapter.add(result._package);
                textName.setText(result._package.getPackage_name());
                textPrice.setText(result._package.getPackage_price()+"원");
                textCount.setText(" / "+result._package.getPackage_count()+"개");
                textDetail.setText(result._package.getPackage_detailinfo());
                textDetail2.setText(result._package.getPackage_subdetailinfo());
                textRecipe.setText(result._package.getPackage_recipeinfo());
                textMemName.setText(result.member.getMem_Name());
                textMemLocation.setText(result.member.getMem_locaname());
                Glide.with(imageView2.getContext()).load(result._package.getPackage_personpicture()).into(imageView2);
                if(result._package.package_state==1){
                    key= false;
                }
                if(!key){
                    btn.setClickable(false);
                }


                int i = result.member.getMem_sellcount();
                int img[]={
                        R.drawable.rank0, R.drawable.rank1, R.drawable.rank2,
                        R.drawable.rank3,R.drawable.rank4,R.drawable.rank5
                };

                if(i==0){

                }
                else if(i>0 && i<10){
                    Glide.with(imageView4.getContext()).load(img[0]).into(imageView4);
                }
                else if(i<30){
                    Glide.with(imageView4.getContext()).load(img[1]).into(imageView4);
                }
                else if(i<50){
                    Glide.with(imageView4.getContext()).load(img[2]).into(imageView4);
                }
                else if(i<80){
                    Glide.with(imageView4.getContext()).load(img[3]).into(imageView4);
                }
                else if(i<100){
                    Glide.with(imageView4.getContext()).load(img[4]).into(imageView4);
                }
                else if(i>=100){
                    Glide.with(imageView4.getContext()).load(img[5]).into(imageView4);
                }





            }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(MyProductDetailActivity.this,"불러올 수 없습니다.",Toast.LENGTH_SHORT).show();

            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
