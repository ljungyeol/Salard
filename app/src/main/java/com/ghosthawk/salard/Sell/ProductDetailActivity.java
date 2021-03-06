package com.ghosthawk.salard.Sell;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ghosthawk.salard.Data.Member;
import com.ghosthawk.salard.Data.MessageDeal;
import com.ghosthawk.salard.Data.ProductResult;
import com.ghosthawk.salard.Data.SuccessCode;
import com.ghosthawk.salard.Manager.DataConstant;
import com.ghosthawk.salard.Manager.DataManager;
import com.ghosthawk.salard.Manager.NetworkManager;
import com.ghosthawk.salard.Manager.PropertyManager;
import com.ghosthawk.salard.Other.OtherMemberInfoActivity;
import com.ghosthawk.salard.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class ProductDetailActivity extends AppCompatActivity{

    public static final String EXTRA_ID="_id";
    public static final String EXTRA_MY_ID="my_id";

    int id;
    String _id;
    String my_id;
    String main_id;
    String person_id;
    boolean state=true;
    ProductDetailPagerAdapter mAdapter;
    ImageView imageProduct,imageMem,imageRank,imageLeft,imageRight;
    TextView textName,textPrice,textCount,textDetail,textDetail2,textRecipe;
    TextView textMemName,textMemLocation,textView;
    Button btn,btn1;
    Member member = new Member();
    boolean key=true;
    ViewPager viewpager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // PackageProduct pack =(PackageProduct) getIntent().getSerializableExtra("ff");
        if (Build.VERSION.SDK_INT >= 21) {   //상태바 색
            getWindow().setStatusBarColor(Color.parseColor("#00d076"));
        }

        id = getIntent().getIntExtra(EXTRA_ID,0);
        setContentView(R.layout.activity_product_detail);

        _id=String.valueOf(id);
        main_id = PropertyManager.getInstance().getId();

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
//        imageProduct = (ImageView)findViewById(R.id.imageView2);
        imageMem = (ImageView)findViewById(R.id.img_mem);
        imageRank = (ImageView)findViewById(R.id.img_rank);
        imageRight = (ImageView)findViewById(R.id.image_right);
        imageLeft = (ImageView)findViewById(R.id.image_left);
        //int i =getIntent().getIntExtra(EXTRA_Picture, 0);
        //imageView.setImageResource(i);
       // Glide.with(imageView.getContext()).load(pack.package_mainpicture).into(imageView);
        textName = (TextView)findViewById(R.id.text_name);

        textPrice=(TextView)findViewById(R.id.text_price);

        textCount=(TextView)findViewById(R.id.text_count);

        textDetail=(TextView)findViewById(R.id.text_detail);

        textDetail2=(TextView)findViewById(R.id.text_detail2);

        textRecipe=(TextView)findViewById(R.id.text_recipe);

        textMemName = (TextView)findViewById(R.id.text_name2);
        textMemLocation = (TextView)findViewById(R.id.text_location);
        btn1 = (Button)findViewById(R.id.btn_message);

        btn = (Button)findViewById(R.id.btn_follow);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(key){
                    NetworkManager.getInstance().getLike(this, _id, main_id, new NetworkManager.OnResultListener<SuccessCode>() {
                        @Override
                        public void onSuccess(Request request, SuccessCode result) {
                            Toast.makeText(ProductDetailActivity.this,"찜했습니다.",Toast.LENGTH_SHORT).show();
                            btn.setPressed(true);
                            key=false;
                        }

                        @Override
                        public void onFail(Request request, IOException exception) {
                            Toast.makeText(ProductDetailActivity.this,"불러올 수 없습니다.",Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                else{
                    NetworkManager.getInstance().getDisLike(this, _id, main_id, new NetworkManager.OnResultListener<SuccessCode>() {
                        @Override
                        public void onSuccess(Request request, SuccessCode result) {
                            btn.setPressed(false);
                            key=true;
                        }

                        @Override
                        public void onFail(Request request, IOException exception) {
                            Toast.makeText(ProductDetailActivity.this,"불러올 수 없습니다.",Toast.LENGTH_SHORT).show();

                        }
                    });



                }
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





        //init(pro);


        //boolean a =pro.product_Soldout;
        //int i = pro.getProduct_Mem_Id();




        imageMem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailActivity.this, OtherMemberInfoActivity.class);
                intent.putExtra(OtherMemberInfoActivity.EXTRA_PERSON_ID,person_id);
               /*
                intent.putExtra("aa", textMemName.getText());
                intent.putExtra("bb", textMemStatmsg.getText());
                intent.putExtra("cc", textMemFollow.getText());
                intent.putExtra("dd", textMemLocation.getText());
                */

                startActivity(intent);
            }
        });
    }
    public void onResume(){
        super.onResume();
        init();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        NetworkManager.getInstance().getProductDetail(this,_id, main_id, new NetworkManager.OnResultListener<ProductResult>() {
            @Override
            public void onSuccess(Request request, ProductResult result) {


                mAdapter.add(result._package);
//                Glide.with(imageProduct.getContext()).load(result._package.getPackage_mainpicture()).into(imageProduct);
                textName.setText(result._package.getPackage_name());
                textPrice.setText(result._package.getPackage_price()+"원");
                textCount.setText(result._package.getPackage_count()+"개");
                textDetail.setText(result._package.getPackage_detailinfo());
                textDetail2.setText(result._package.getPackage_subdetailinfo());
                textRecipe.setText(result._package.getPackage_recipeinfo());
                textMemName.setText(result.member.getMem_Name());
                textMemLocation.setText(result.member.getMem_locaname());
                Glide.with(imageMem.getContext()).load(result.member.getMem_Picture()).into(imageMem);
                my_id = result.member.getMem_id();
                person_id = result._package.getPackage_personid();
                for(int i=0;i<result.mywish.mem_wishlist.size();i++){
                    if (result._package._id==result.mywish.mem_wishlist.get(i)){
                        //위시리스트에 있으면 false
                        key=false;
                        break;
                    }
                }
                if(result._package.getPackage_state()==1){
                    //1이면 판매 완료
                    state = false;
                }
                if(!key){
                    //btn.setEnabled(false);
                    btn.setPressed(true);
                }

                if (state){
                    btn1.setPressed(false);
                    btn1.setClickable(true);
                }
                else {
                    btn1.setPressed(true);
                }



                int i = result.member.getMem_sellcount();
                int img[]={
                        R.drawable.rank0, R.drawable.rank1, R.drawable.rank2,
                        R.drawable.rank3,R.drawable.rank4,R.drawable.rank5
                };

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

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(ProductDetailActivity.this,"불러올 수 없습니다.",Toast.LENGTH_SHORT).show();
            }
        });



//--TODO 이거 거래신청 이제 GCM으로 해야된다.
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state){
                    NetworkManager.getInstance().getSendDeal(this, main_id, person_id, _id, new NetworkManager.OnResultListener<MessageDeal>() {
                        @Override
                        public void onSuccess(Request request,MessageDeal result) {
                            Toast.makeText(ProductDetailActivity.this,"식재료를 신청했습니다.",Toast.LENGTH_SHORT).show();

                            String id = DataManager.getInstance().getUserTableId(result.message);
                            DataManager.getInstance().addChatMessage(id,result.message.member, result.message.msg_packagenum,DataConstant.ChatTable.TYPE_SEND,result.message.msg_content,result.message.msg_date);


                        }

                        @Override
                        public void onFail(Request request, IOException exception) {

                        }
                    });
                }
            }
        });




    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.product_detail,menu);
//        return true;
//    }
}
