package com.ghosthawk.salard.Sell;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ghosthawk.salard.Data.Member;
import com.ghosthawk.salard.Data.MyPageModifyResult;
import com.ghosthawk.salard.Data.SuccessCode;
import com.ghosthawk.salard.Manager.NetworkManager;
import com.ghosthawk.salard.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class SellHomeModifyActivity extends AppCompatActivity {
    public static final String EXTRA_MY_ID = "my_id";
    EditText editName,editStatmsg;
    ImageView imageMy, imageOk;
    String my_id;
    String[] items = {"카메라로 촬영","앨범에서 선택"};
    String mem_name, mem_statemsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_home_modify);
        my_id = getIntent().getStringExtra(EXTRA_MY_ID);
        editName = (EditText)findViewById(R.id.edit_name);
        editStatmsg = (EditText)findViewById(R.id.edit_statmsg);
        imageMy = (ImageView)findViewById(R.id.img_my);
        imageOk = (ImageView)findViewById(R.id.img_modify);

        NetworkManager.getInstance().getMyPageUpdate(this, my_id, new NetworkManager.OnResultListener<MyPageModifyResult>() {
            @Override
            public void onSuccess(Request request, MyPageModifyResult result) {
                editName.setText(result.member.getMem_Name());
                editStatmsg.setText(result.member.getMem_StatMsg());
                Glide.with(imageMy.getContext()).load(result.member.getMem_Picture()).into(imageMy);

            }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(SellHomeModifyActivity.this,"실패 수고",Toast.LENGTH_SHORT).show();

            }
        });

        imageMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(SellHomeModifyActivity.this);
                builder1.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case 0:
                                getImageFromCamera();
                                break;
                            case 1:
                                getImageFromGallery();
                                break;
                        }

                    }
                });
                AlertDialog dialog = builder1.create();
                ListView listView = dialog.getListView();
                dialog.show();
            }
        });


        imageOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SellHomeModifyActivity.this);
                builder.setMessage("내 정보를 수정하시겠습니까?");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mem_name = editName.getText().toString();
                        mem_statemsg = editStatmsg.getText().toString();

                        NetworkManager.getInstance().getMyPageUpdateComplete(this, mem_name,mem_statemsg,mUploadFile,my_id, new NetworkManager.OnResultListener<SuccessCode>() {
                            @Override
                            public void onSuccess(Request request, SuccessCode result) {
                                Toast.makeText(SellHomeModifyActivity.this,"수정되었습니다.",Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            Intent intent;


                            @Override
                            public void onFail(Request request, IOException exception) {
                                Toast.makeText(SellHomeModifyActivity.this,"수정 실패!!.",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SellHomeModifyActivity.this,"취소되었습니다.",Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();

    }





    private static final int RC_GALLERY= 1;
    private static final int RC_CAMERA = 2;



    private void getImageFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //촬영
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getCameraCaptureFile()); //저장
        startActivityForResult(intent, RC_CAMERA);
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

    private void getImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/jpeg");
        startActivityForResult(intent, RC_GALLERY);
    }







    File mUploadFile = null;





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                String[] projection = {MediaStore.Images.Media.DATA};
                Cursor c = this.getContentResolver().query(uri, projection, null, null, null);
                if (c.moveToNext()) {
                    String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                    mUploadFile=new File(path);
                    BitmapFactory.Options opts = new BitmapFactory.Options();
                    opts.inSampleSize = 2;
                    Bitmap bm = BitmapFactory.decodeFile(path, opts);
                    imageMy.setImageBitmap(bm);
                }
            }
            return;
        }


        if (requestCode == RC_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                File file = mCameraCaptureFile;
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inSampleSize = 2;
                Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
                imageMy.setImageBitmap(bm);
                mUploadFile = file;
            }
            return;
        }

        onActivityResult(requestCode, resultCode, data);
    }



}
