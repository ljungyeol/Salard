package com.ghosthawk.salard.Sell;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ghosthawk.salard.Data.Member;
import com.ghosthawk.salard.Data.PackageProduct;
import com.ghosthawk.salard.Data.ProductResult;
import com.ghosthawk.salard.Data.SuccessCode;
import com.ghosthawk.salard.Manager.NetworkManager;
import com.ghosthawk.salard.R;

import java.io.IOException;
import java.util.zip.Inflater;

import okhttp3.Request;

public class ProductDetailActivity extends AppCompatActivity {
    public static final String EXTRA_Name = "name";
    public static final String EXTRA_Price = "price";
    public static final String EXTRA_Count = "count";
    public static final String EXTRA_Picture = "Pirture";
    public static final String EXTRA_Detail = "detail1";
    public static final String EXTRA_Detail2 = "detail2";
    public static final String EXTRA_Recipe="recipe";
    public static final String EXTRA_ID="_id";
    public static final String EXTRA_MY_ID="my_id";

    int id;
    String _id;
    String my_id;
    String main_id;
    String person_id;
    boolean state=true;
    ImageView imageProduct,imageMem,imageRank;
    TextView textName,textPrice,textCount,textDetail,textDetail2,textRecipe;
    TextView textMemName,textMemLocation,textView;
    Button btn,btn1;
    Member member = new Member();
    boolean key=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // PackageProduct pack =(PackageProduct) getIntent().getSerializableExtra("ff");
//        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        id = getIntent().getIntExtra(EXTRA_ID,0);
        setContentView(R.layout.activity_product_detail);
        _id=String.valueOf(id);
        main_id = getIntent().getStringExtra(EXTRA_MY_ID);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView = (TextView)findViewById(R.id.text_title);
        textView.setText("식재료 정보");
        imageProduct = (ImageView)findViewById(R.id.imageView2);
        imageMem = (ImageView)findViewById(R.id.img_mem);

        imageRank = (ImageView)findViewById(R.id.img_rank);
        //int i =getIntent().getIntExtra(EXTRA_Picture, 0);
        //imageView.setImageResource(i);
       // Glide.with(imageView.getContext()).load(pack.package_mainpicture).into(imageView);
        textName = (TextView)findViewById(R.id.text_name);
        //textName.setText(getIntent().getStringExtra(EXTRA_Name));
      //  textName.setText(pack.getPackage_name());
        textPrice=(TextView)findViewById(R.id.text_price);
        //textPrice.setText(getIntent().getIntExtra(EXTRA_Price, 0)+"원");
     //   textPrice.setText(pack.getPackage_price()+"원");
        textCount=(TextView)findViewById(R.id.text_count);
      //  textCount.setText(" / "+getIntent().getIntExtra(EXTRA_Count,0)+"개");
     //   textCount.setText(" / "+pack.getPackage_count()+"개");
        textDetail=(TextView)findViewById(R.id.text_detail);
       // textDetail.setText(getIntent().getStringExtra(EXTRA_Detail));
       // textDetail.setText(pack.getPackage_detailinfo());
        textDetail2=(TextView)findViewById(R.id.text_detail2);
       // textDetail2.setText(getIntent().getStringExtra(EXTRA_Detail2));
      //  textDetail2.setText(pack.getPackage_subdetailinfo());
        textRecipe=(TextView)findViewById(R.id.text_recipe);
        //textRecipe.setText(getIntent().getStringExtra(EXTRA_Recipe));
     //   textRecipe.setText(pack.getPackage_recipeinfo());

        textMemName = (TextView)findViewById(R.id.text_name2);
        textMemLocation = (TextView)findViewById(R.id.text_location);
        btn = (Button)findViewById(R.id.btn_follow);
        btn.setText("찜하기");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(key){
                    NetworkManager.getInstance().getLike(this, _id, main_id, new NetworkManager.OnResultListener<SuccessCode>() {
                        @Override
                        public void onSuccess(Request request, SuccessCode result) {
                            btn.setText("넌 이미 찜해있다");
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
                            btn.setText("찜하기");
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

        btn1 = (Button)findViewById(R.id.btn_message);







        //init(pro);


        //boolean a =pro.product_Soldout;
        //int i = pro.getProduct_Mem_Id();




        imageMem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailActivity.this, OtherMemberInfoActivity.class);
                intent.putExtra(OtherMemberInfoActivity.EXTRA_MY_ID,my_id);
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
    private void init() {
        NetworkManager.getInstance().getProductDetail(this,_id, main_id, new NetworkManager.OnResultListener<ProductResult>() {
            @Override
            public void onSuccess(Request request, ProductResult result) {
               // Log.d("ddddddddddddddddddd",result._package.getPackage_mainpicture());
                Glide.with(imageProduct.getContext()).load(result._package.getPackage_mainpicture()).into(imageProduct);
                textName.setText(result._package.getPackage_name());
                textPrice.setText(result._package.getPackage_price()+"원");
                textCount.setText(result._package.getPackage_count()+"개");
                textDetail.setText(result._package.getPackage_detailinfo());
                textDetail2.setText(result._package.getPackage_subdetailinfo());
                textRecipe.setText(result._package.getPackage_recipeinfo());
                textMemName.setText(result.member.getMem_Name());
                textMemLocation.setText(result._package.getPackage_loca());
                Log.d("state",result._package.getPackage_state()+"");
                Log.d("ddddddddddddddd",state+"");

                Glide.with(imageMem.getContext()).load(result._package.getPackage_personpicture()).into(imageMem);
                my_id = result.member.getMem_id();
                person_id = result._package.getPackage_personid();
                for(int i=0;i<result.mywish.mem_wishlist.size();i++){
                    if (result._package._id==result.mywish.mem_wishlist.get(i)){
                        key=false;
                        break;
                    }
                }
                if(result._package.getPackage_state()==1){
                    state = false;
                }
                if(!key){
                    //btn.setEnabled(false);
                    btn.setText("넌 이미 찜했다");
                }

                if (state){
                    btn1.setText("거래신청");
                }
                else
                    btn1.setText("거래불가");


            }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(ProductDetailActivity.this,"불러올 수 없습니다.",Toast.LENGTH_SHORT).show();
            }
        });




        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state){
                    NetworkManager.getInstance().getSendDeal(this, my_id, person_id, _id, new NetworkManager.OnResultListener<SuccessCode>() {
                        @Override
                        public void onSuccess(Request request, SuccessCode result) {
                            Toast.makeText(ProductDetailActivity.this,"성공!!",Toast.LENGTH_SHORT).show();

                            state=false;
                        }

                        @Override
                        public void onFail(Request request, IOException exception) {

                        }
                    });
                }
            }
        });


/*
            member.mem_follow = product.getProduct_Mem_Id()+1000;
            member.mem_Name = "이정열";
            member.mem_StatMsg = "개발 어렵다 집에 가고 싶어";

            imageView2.setImageResource(product.getProduct_Picture());
            textMemName.setText(member.getMem_Name());
            textMemStatmsg.setText(member.getMem_StatMsg());
            textMemLocation.setText("지옥같은 서울대 연구공원 티아카데미");
            textMemFollow.setText(member.getMem_follow()+"");*/


    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.product_detail,menu);
//        return true;
//    }
}
