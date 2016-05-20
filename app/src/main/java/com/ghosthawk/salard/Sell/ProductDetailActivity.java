package com.ghosthawk.salard.Sell;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghosthawk.salard.Data.Member;
import com.ghosthawk.salard.Data.Product;
import com.ghosthawk.salard.R;

import java.util.zip.Inflater;

public class ProductDetailActivity extends AppCompatActivity {
    public static final String EXTRA_Name = "name";
    public static final String EXTRA_Price = "price";
    public static final String EXTRA_Count = "count";
    public static final String EXTRA_Picture = "Pirture";
    public static final String EXTRA_Detail = "detail1";
    public static final String EXTRA_Detail2 = "detail2";
    public static final String EXTRA_Recipe="recipe";


    String code;
    ImageView imageView,imageView2;
    TextView textName,textPrice,textCount,textDetail,textDetail2,textRecipe;
    TextView textMemName,textMemStatmsg,textMemLocation,textMemFollow;
    Member member = new Member();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Product pro =(Product)getIntent().getSerializableExtra("ff");

        setContentView(R.layout.activity_product_detail);



        imageView = (ImageView)findViewById(R.id.imageView2);
        //int i =getIntent().getIntExtra(EXTRA_Picture, 0);
        //imageView.setImageResource(i);
        imageView.setImageResource(pro.getProduct_Picture());
        textName = (TextView)findViewById(R.id.text_name);
        //textName.setText(getIntent().getStringExtra(EXTRA_Name));
        textName.setText(pro.getProduct_Name());
        textPrice=(TextView)findViewById(R.id.text_price);
        //textPrice.setText(getIntent().getIntExtra(EXTRA_Price, 0)+"원");
        textPrice.setText(pro.getProduct_Price()+"원");
        textCount=(TextView)findViewById(R.id.text_count);
      //  textCount.setText(" / "+getIntent().getIntExtra(EXTRA_Count,0)+"개");
        textCount.setText(" / "+pro.getProduct_Price()+"개");
        textDetail=(TextView)findViewById(R.id.text_detail);
       // textDetail.setText(getIntent().getStringExtra(EXTRA_Detail));
        textDetail.setText(pro.getProduct_Detail());
        textDetail2=(TextView)findViewById(R.id.text_detail2);
       // textDetail2.setText(getIntent().getStringExtra(EXTRA_Detail2));
        textDetail2.setText(pro.getProduct_Detail2());
        textRecipe=(TextView)findViewById(R.id.text_recipe);
        //textRecipe.setText(getIntent().getStringExtra(EXTRA_Recipe));
        textRecipe.setText(pro.getProduct_Recipe());

        textMemName = (TextView)findViewById(R.id.text_name2);
        textMemStatmsg = (TextView)findViewById(R.id.text_statmsg);
        textMemLocation = (TextView)findViewById(R.id.text_location);
        textMemFollow = (TextView)findViewById(R.id.text_follow);
        imageView2 = (ImageView)findViewById(R.id.imageView3);
        init(pro);


        boolean a =pro.product_Soldout;
        int i = pro.getProduct_Mem_Id();




        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailActivity.this, OtherMemberInfoActivity.class);
                intent.putExtra("aa", textMemName.getText());
                intent.putExtra("bb", textMemStatmsg.getText());
                intent.putExtra("cc", textMemFollow.getText());
                intent.putExtra("dd", textMemLocation.getText());

                startActivity(intent);
            }
        });
    }

    private void init(Product product) {

            member.mem_follow = product.getProduct_Mem_Id()+1000;
            member.mem_Name = "이정열";
            member.mem_StatMsg = "개발 어렵다 집에 가고 싶어";

            imageView2.setImageResource(product.getProduct_Picture());
            textMemName.setText(member.getMem_Name());
            textMemStatmsg.setText(member.getMem_StatMsg());
            textMemLocation.setText("지옥같은 서울대 연구공원 티아카데미");
            textMemFollow.setText(member.getMem_follow()+"");


    }
}
