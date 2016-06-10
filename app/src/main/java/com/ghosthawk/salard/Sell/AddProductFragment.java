package com.ghosthawk.salard.Sell;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ghosthawk.salard.Data.SuccessCode;
import com.ghosthawk.salard.Manager.NetworkManager;
import com.ghosthawk.salard.Manager.PropertyManager;
import com.ghosthawk.salard.R;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.

 */
public class AddProductFragment extends Fragment {
    ImageView imageView,imageView2;
    TextView textNumber,textSub;
    EditText editName,editDetail,editRecipe,editPrice,editSubDetail;
    Button btnPlus,btnMinus,btnRegist;
    String food_name, food_detailinfo,food_subdetailinfo,food_recipeinfo,food_price,food_count;
    int i=0;
    String xloca,yloca;
    Intent intent1,intent2;
    boolean a = true;
    String mem_id;
    public AddProductFragment() {
        // Required empty public constructor
    }

    String[] items = {"카메라로 촬영","앨범에서 선택"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);
        textNumber =(TextView)view.findViewById(R.id.text_number) ;
        btnPlus = (Button)view.findViewById(R.id.btn_plus);
        btnMinus = (Button)view.findViewById(R.id.btn_minus);
        btnRegist = (Button)view.findViewById(R.id.btn_regist);
        imageView = (ImageView)view.findViewById(R.id.image_picture);
        imageView2 = (ImageView)view.findViewById(R.id.image_picture2);
        editName=(EditText)view.findViewById(R.id.edit_name);
        editDetail = (EditText)view.findViewById(R.id.edit_detail);
        editRecipe=(EditText)view.findViewById(R.id.edit_recipe);
        editPrice =(EditText)view.findViewById(R.id.edit_price);
        textSub=(TextView)view.findViewById(R.id.text_subbutton);
        editSubDetail = (EditText)view.findViewById(R.id.edit_subdetail);
        mem_id = PropertyManager.getInstance().getId();
        xloca = Double.toString(PropertyManager.getInstance().getMember().getMem_xloca());
        yloca = Double.toString(PropertyManager.getInstance().getMember().getMem_yloca());
        Log.d("aaaaaaaaaa",xloca);
        Log.d("bbbbbbbbbbbb",yloca);


        textSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editSubDetail.getVisibility()==v.GONE)
                    editSubDetail.setVisibility(View.VISIBLE);
                else
                    editSubDetail.setVisibility(View.GONE);
            }
        });
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


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("식재료 등록을 완료하시겠습니까?");
                builder.setPositiveButton("완료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        food_name = editName.getText().toString();
                        food_count = textNumber.getText().toString();
                        food_detailinfo = editDetail.getText().toString();
                        food_subdetailinfo = editSubDetail.getText().toString();
                        if (TextUtils.isEmpty(food_subdetailinfo)){
                            food_subdetailinfo = "";
                        }
                        food_recipeinfo = editRecipe.getText().toString();
                        food_price = editPrice.getText().toString();

                        if(TextUtils.isEmpty(food_name) || food_count.equals("0") || TextUtils.isEmpty(food_detailinfo)
                                || TextUtils.isEmpty(food_recipeinfo) || TextUtils.isEmpty(food_price) || mUploadFile.get(0)==null || mUploadFile.get(1)==null){
                            Toast.makeText(getContext(),"빈 곳이 있습니다.",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        NetworkManager.getInstance().getAddFood(getContext(), mem_id, food_name, food_detailinfo, food_subdetailinfo, food_recipeinfo, food_price,  food_count,mUploadFile,
                               xloca,yloca ,new NetworkManager.OnResultListener<SuccessCode>() {
                            @Override
                            public void onSuccess(Request request, SuccessCode result) {
                                Toast.makeText(getContext(),"완료되었습니다.",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFail(Request request, IOException exception) {
                                Toast.makeText(getContext(),"등록을 실패하였습니다.",Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(),"취소되었습니다.",Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });





        return view;






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
        File dir = getContext().getExternalFilesDir("Salard");
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
                Cursor c = getContext().getContentResolver().query(uri, projection, null, null, null);
                if (c.moveToNext()) {
                    String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                    mUploadFile.add(0,new File(path));
                    Glide.with(imageView.getContext()).load(path).into(imageView);
/*
                    BitmapFactory.Options opts = new BitmapFactory.Options();
                    opts.inSampleSize = 2;
                    Bitmap bm = BitmapFactory.decodeFile(path, opts);
                    imageView.setImageBitmap(bm);*/
                }
            }
            return;
        }
        if (requestCode == RC_GALLERY2) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                String[] projection = {MediaStore.Images.Media.DATA};
                Cursor c = getContext().getContentResolver().query(uri, projection, null, null, null);
                if (c.moveToNext()) {
                    String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                    mUploadFile.add(1,new File(path));
                    Glide.with(imageView2.getContext()).load(path).into(imageView2);
/*
                    BitmapFactory.Options opts = new BitmapFactory.Options();
                    opts.inSampleSize = 2;
                    Bitmap bm = BitmapFactory.decodeFile(path, opts);
                    imageView2.setImageBitmap(bm);*/
                }
            }
            return;
        }







        if (requestCode == RC_CAMERA1) {
            if (resultCode == Activity.RESULT_OK) {
                File file = mCameraCaptureFile;
                Glide.with(imageView.getContext()).load(file.getAbsolutePath()).into(imageView);

//                BitmapFactory.Options opts = new BitmapFactory.Options();
//                opts.inSampleSize = 2;
//                Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
//                imageView.setImageBitmap(bm);
                mUploadFile.add(0,file);
            }
            return;
        }

        if (requestCode == RC_CAMERA2) {
            if (resultCode == Activity.RESULT_OK) {
                File file = mCameraCaptureFile;
                Glide.with(imageView2.getContext()).load(file.getAbsolutePath()).into(imageView2);

//                BitmapFactory.Options opts = new BitmapFactory.Options();
//                opts.inSampleSize = 2;
//                Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
//                imageView2.setImageBitmap(bm);
                mUploadFile.add(1,file);
            }
            return;
        }

       onActivityResult(requestCode, resultCode, data);
    }



}
