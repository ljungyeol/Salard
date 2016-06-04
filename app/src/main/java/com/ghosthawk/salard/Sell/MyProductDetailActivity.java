package com.ghosthawk.salard.Sell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ghosthawk.salard.Data.ProductResult;
import com.ghosthawk.salard.Data.SuccessCode;
import com.ghosthawk.salard.Manager.NetworkManager;
import com.ghosthawk.salard.R;

import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Request;

public class MyProductDetailActivity extends AppCompatActivity {
    ImageView imageView,imageView2,imageView3, imageView4;
    TextView textName,textCount,textPrice,textDetail,textDetail2,textRecipe;
    TextView textMemName,textMemStat,textMemLocation;
    Button btn;
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
        id = getIntent().getIntExtra(EXTRA_ID,0);
        _id=String.valueOf(id);
        main_id = getIntent().getStringExtra(EXTRA_MY_ID);
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
        textMemStat = (TextView)findViewById(R.id.text_statmsg);
        textMemLocation = (TextView)findViewById(R.id.text_location);

        btn = (Button)findViewById(R.id.btn_sell);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(key){
                    NetworkManager.getInstance().getSoldout(this, _id, new NetworkManager.OnResultListener<SuccessCode>() {
                        @Override
                        public void onSuccess(Request request, SuccessCode result) {
                            btn.setText("판매완료 취소");
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
                            btn.setText("판매완료");
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
    }



    public void onResume() {
        super.onResume();
            init();
    }

    private void init() {
        NetworkManager.getInstance().getProductDetail(this, _id, main_id, new NetworkManager.OnResultListener<ProductResult>() {
            @Override
            public void onSuccess(Request request, ProductResult result) {
                Glide.with(imageView.getContext()).load(result._package.getPackage_mainpicture()).into(imageView);
                textName.setText(result._package.getPackage_name());
                textPrice.setText(result._package.getPackage_price()+"원");
                textCount.setText(" / "+result._package.getPackage_count()+"개");
                textDetail.setText(result._package.getPackage_detailinfo());
                textDetail2.setText(result._package.getPackage_subdetailinfo());
                textRecipe.setText(result._package.getPackage_recipeinfo());
                textMemName.setText(result.member.getMem_Name());
                textMemStat.setText(result.member.getMem_StatMsg());
                textMemLocation.setText(result._package.getPackage_loca());
                Glide.with(imageView2.getContext()).load(result._package.getPackage_personpicture()).into(imageView2);
                if(result._package.package_state==1){
                    key= false;
                }
                if(!key){
                    btn.setText("판매완료 취소");
                }
            }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(MyProductDetailActivity.this,"불러올 수 없습니다.",Toast.LENGTH_SHORT).show();

            }
        });


    }
}
