package com.ghosthawk.salard.Login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ghosthawk.salard.Data.SuccessCode;
import com.ghosthawk.salard.Manager.NetworkManager;
import com.ghosthawk.salard.R;

import java.io.File;
import java.io.IOException;

import okhttp3.Request;

public class MembershipActivity extends AppCompatActivity {
    ImageView imageMem;
    EditText textName,textEmail,textPhone,textStatmsg,textId,textPw;
    String[] items = {"카메라로 촬영","앨범에서 선택"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);
        imageMem = (ImageView) findViewById(R.id.img_pic);
        textName = (EditText)findViewById(R.id.edit_name);
        textEmail = (EditText)findViewById(R.id.edit_email);
        textPhone = (EditText)findViewById(R.id.edit_phone);
        textStatmsg = (EditText)findViewById(R.id.edit_statemsg);
        textId =(EditText)findViewById(R.id.edit_id);
        textPw = (EditText)findViewById(R.id.edit_pw);

        imageMem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MembershipActivity.this);
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



        Button btn = (Button)findViewById(R.id.btn_membership);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = textName.getText().toString();
                final String email = textEmail.getText().toString();
                final String phone = textPhone.getText().toString();
                String statemsg = textStatmsg.getText().toString();
                final String password = textPw.getText().toString();
                String id = textId.getText().toString();

                if(TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches() || mUploadFile==null
                        || TextUtils.isEmpty(password) || TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)
                        || TextUtils.isEmpty(statemsg) || TextUtils.isEmpty(id) || !Patterns.PHONE.matcher(phone).matches()){
                    Toast.makeText(MembershipActivity.this,"잘못 입력하거나 빈 곳이 있습니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
                NetworkManager.getInstance().getSignUp(this, id, name, statemsg, password, email, phone, mUploadFile, new NetworkManager.OnResultListener<SuccessCode>() {
                    @Override
                    public void onSuccess(Request request, SuccessCode result) {
                        Toast.makeText(MembershipActivity.this,"회원가입이 완료 되었습니다. 로그인 해주세요",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFail(Request request, IOException exception) {
                        Toast.makeText(MembershipActivity.this,"회원가입을 실패했습니다.",Toast.LENGTH_SHORT).show();
                    }
                });
                //--TODO 여기에 네트워크 매니저 태광이 오면 확인
            }
        });
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
                    Glide.with(imageMem.getContext()).load(path).into(imageMem);

                }
            }
            return;
        }


        if (requestCode == RC_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                File file = mCameraCaptureFile;
                Glide.with(imageMem.getContext()).load(file.getAbsolutePath()).into(imageMem);

                mUploadFile = file;
            }
            return;
        }

        onActivityResult(requestCode, resultCode, data);
    }


}
