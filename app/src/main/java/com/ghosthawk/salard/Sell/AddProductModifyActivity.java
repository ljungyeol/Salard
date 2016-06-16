package com.ghosthawk.salard.Sell;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ghosthawk.salard.Data.SuccessCode;
import com.ghosthawk.salard.Data.UpdateProductResult;
import com.ghosthawk.salard.Manager.NetworkManager;
import com.ghosthawk.salard.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class AddProductModifyActivity extends AppCompatActivity {
    public static final String EXTRA_ID="_id";
    ImageView imageView, imageView2;
    TextView textNumber,textSub;
    EditText editName,editDetail,editRecipe,editPrice,editSubDetail;
    Button btnPlus,btnMinus,btnRegist;
    String[] items = {"카메라로 촬영","앨범에서 선택"};
    Intent intent1,intent2;
    String food_name, food_detailinfo,food_subdetailinfo,food_recipeinfo,food_price,food_count;
    int i ;
    int id;
    String _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_modify);
        if (Build.VERSION.SDK_INT >= 21) {   //상태바 색
            getWindow().setStatusBarColor(Color.parseColor("#00d076"));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.selector_action_back);
        textNumber =(TextView)findViewById(R.id.text_number) ;
        textSub = (TextView)findViewById(R.id.text_subbutton) ;
        btnPlus = (Button)findViewById(R.id.btn_plus);
        btnMinus = (Button)findViewById(R.id.btn_minus);
        btnRegist = (Button)findViewById(R.id.btn_regist);
        imageView = (ImageView)findViewById(R.id.image_picture);
        imageView2 = (ImageView)findViewById(R.id.image_picture2);
        editName=(EditText)findViewById(R.id.edit_name);
        editDetail = (EditText)findViewById(R.id.edit_detail);
        editRecipe=(EditText)findViewById(R.id.edit_recipe);
        editPrice =(EditText)findViewById(R.id.edit_price);
        editSubDetail=(EditText)findViewById(R.id.edit_subdetail);

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i>0){
                    i--;
                    textNumber.setText(i+"");
                }
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                textNumber.setText(i+"");
            }
        });

        textSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editSubDetail.getVisibility() == v.GONE) {
                    editSubDetail.setVisibility(View.VISIBLE);

                } else {
                    editSubDetail.setVisibility(View.GONE);
                }
            }
        });


        _id=getIntent().getStringExtra(EXTRA_ID);
        //id = getIntent().getIntExtra(EXTRA_ID,0);
        //_id=String.valueOf(id);

        NetworkManager.getInstance().getPackageUpdate(this, _id, new NetworkManager.OnResultListener<UpdateProductResult>() {
            @Override
            public void onSuccess(Request request, UpdateProductResult result) {
                editName.setText(result.food.getPackage_name());
                editPrice.setText(result.food.getPackage_price()+"");
                editDetail.setText(result.food.getPackage_detailinfo());
                editRecipe.setText(result.food.getPackage_recipeinfo());
                textNumber.setText(result.food.getPackage_count()+"");
                editSubDetail.setText(result.food.getPackage_subdetailinfo());
                i = result.food.getPackage_count();
                Glide.with(imageView.getContext()).load(result.food.getPackage_mainpicture()).into(imageView);
                Glide.with(imageView2.getContext()).load(result.food.getPackage_subpicture()).into(imageView2);
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddProductModifyActivity.this);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch(which){
                            case 0:
                                getImageFromCamera(intent1,RC_CAMERA1);
                                break;
                            case 1:
                                getImageFromGallery(intent1,RC_GALLERY1);
                                break;
                        }

                    }
                });
                AlertDialog dialog = builder.create();
                ListView listView = dialog.getListView();
                dialog.show();
            }
        });


        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(AddProductModifyActivity.this);
                builder1.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case 0:
                                getImageFromCamera(intent2,RC_CAMERA2);
                                break;
                            case 1:
                                getImageFromGallery(intent2,RC_GALLERY2);
                                break;
                        }

                    }
                });
                AlertDialog dialog = builder1.create();
                ListView listView = dialog.getListView();
                dialog.show();
            }
        });



        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddProductModifyActivity.this);
                builder.setMessage("식재료를 삭제하시겠습니까?");
                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NetworkManager.getInstance().getFoodRemove(this, _id, new NetworkManager.OnResultListener<SuccessCode>() {
                            @Override
                            public void onSuccess(Request request, SuccessCode result) {
                                Toast.makeText(AddProductModifyActivity.this,"삭제되었습니다.",Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFail(Request request, IOException exception) {

                            }
                        });
                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AddProductModifyActivity.this,"취소되었습니다.",Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.modify_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            food_name = editName.getText().toString();
            food_count = textNumber.getText().toString();
            food_detailinfo = editDetail.getText().toString();
            food_recipeinfo = editRecipe.getText().toString();
            food_subdetailinfo = editSubDetail.getText().toString();
            food_price = editPrice.getText().toString();
            if (TextUtils.isEmpty(food_subdetailinfo)){
                food_subdetailinfo = "";
            }
            if(TextUtils.isEmpty(food_name) || food_count.equals("0") || TextUtils.isEmpty(food_detailinfo)
                    || TextUtils.isEmpty(food_recipeinfo) || TextUtils.isEmpty(food_price) || mUploadFile.get(0)==null || mUploadFile.get(1)==null){
                Toast.makeText(AddProductModifyActivity.this,"빈 곳이 있습니다.",Toast.LENGTH_SHORT).show();
                return false;
                //리턴값은 어떻게하나
            }



            NetworkManager.getInstance().getPackageUpdateComplete(this, _id, food_name, mUploadFile, food_price, food_recipeinfo, food_detailinfo, food_subdetailinfo, food_count, new NetworkManager.OnResultListener<SuccessCode>() {
                @Override
                public void onSuccess(Request request, SuccessCode result) {
                    Toast.makeText(AddProductModifyActivity.this, "수정완료됏습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFail(Request request, IOException exception) {
                    Toast.makeText(AddProductModifyActivity.this,"식재료 수정을 실패하였습니다.",Toast.LENGTH_SHORT).show();

                }
            });



            return true;
        }


        return super.onOptionsItemSelected(item);
    }






    private static final int RC_GALLERY1= 1;
    private static final int RC_CAMERA1 = 2;
    private static final int RC_GALLERY2 = 3;
    private static final int RC_CAMERA2 = 4;


    private void getImageFromCamera(Intent intent, int a) {
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //촬영
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getCameraCaptureFile()); //저장
        startActivityForResult(intent, a);
    }
    File mCameraCaptureFile;
    private Uri getCameraCaptureFile() {
        File dir = this.getExternalFilesDir("Salard");
        if (!dir.exists()) { //폴더가 없을때 폴더 생성
            dir.mkdirs();
        }
        mCameraCaptureFile = new File(dir, "Salard"+System.currentTimeMillis()+".jpg");
        return Uri.fromFile(mCameraCaptureFile);
    }

    private void getImageFromGallery(Intent intent,int a) {
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/jpeg");
        startActivityForResult(intent, a);
    }







    List<File> mUploadFile = new ArrayList<>();





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GALLERY1) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                String[] projection = {MediaStore.Images.Media.DATA};
                Cursor c = this.getContentResolver().query(uri, projection, null, null, null);
                if (c.moveToNext()) {
                    String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                    mUploadFile.add(0,new File(path));
                    BitmapFactory.Options opts = new BitmapFactory.Options();
                    opts.inSampleSize = 2;
                    Bitmap bm = BitmapFactory.decodeFile(path, opts);
                    imageView.setImageBitmap(bm);
                }
            }
            return;
        }
        if (requestCode == RC_GALLERY2) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                String[] projection = {MediaStore.Images.Media.DATA};
                Cursor c = this.getContentResolver().query(uri, projection, null, null, null);
                if (c.moveToNext()) {
                    String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                    mUploadFile.add(1,new File(path));
                    BitmapFactory.Options opts = new BitmapFactory.Options();
                    opts.inSampleSize = 2;
                    Bitmap bm = BitmapFactory.decodeFile(path, opts);
                    imageView2.setImageBitmap(bm);
                }
            }
            return;
        }







        if (requestCode == RC_CAMERA1) {
            if (resultCode == Activity.RESULT_OK) {
                File file = mCameraCaptureFile;
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inSampleSize = 2;
                Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
                imageView.setImageBitmap(bm);
                mUploadFile.add(0,file);
            }
            return;
        }

        if (requestCode == RC_CAMERA2) {
            if (resultCode == Activity.RESULT_OK) {
                File file = mCameraCaptureFile;
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inSampleSize = 2;
                Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
                imageView2.setImageBitmap(bm);
                mUploadFile.add(1,file);
            }
            return;
        }

        onActivityResult(requestCode, resultCode, data);
    }



}
