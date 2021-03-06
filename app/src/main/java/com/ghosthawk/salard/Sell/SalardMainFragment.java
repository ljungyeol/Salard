package com.ghosthawk.salard.Sell;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ghosthawk.salard.Data.Member;
import com.ghosthawk.salard.Data.PackageProduct;
import com.ghosthawk.salard.Data.PackageProductResult;
import com.ghosthawk.salard.Manager.NetworkManager;
import com.ghosthawk.salard.Manager.PropertyManager;
import com.ghosthawk.salard.Map.AddressInfo;
import com.ghosthawk.salard.Map.MapActivity;
import com.ghosthawk.salard.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.w3c.dom.Text;

import java.io.IOException;
import java.security.ProtectionDomain;
import java.util.List;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalardMainFragment extends Fragment  implements
        GoogleApiClient.OnConnectionFailedListener{
    String my_id;
    Member member;
    TextView textView;
    String dong;

    Button imageLocation,imageMap,imageOkay;
    String xloca, yloca;
    private List<PackageProduct> result;
    Location location;
//    Handler mHandler= new Handler(Looper.getMainLooper()){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if(msg.what==1){
//                result = (List<PackageProduct>) msg.obj;
///*
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inSampleSize = 8;
//
//                String uri = "full addr"+ .package_mainpicture;
//
//                try {
//                    result.bm = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
//
//                } catch (Exception e) {
//                }*/
//
//
//
//                mAdapter.notifyDataSetChanged();
//
//
//            }
//
//
//        }
//    };

    public SalardMainFragment() {
        // Required empty public constructor
    }
    RecyclerView listView;
    ProductAdapter mAdapter;
    GoogleApiClient mClient;
    EditText edittext;
    LinearLayoutManager mLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
//                .enableAutoManage(getActivity(), this)
                .addOnConnectionFailedListener(this)
                .build();
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        location = LocationServices.FusedLocationApi.getLastLocation(mClient);
        mAdapter = new ProductAdapter();
        mAdapter.setOnItemClickListener(new ProductViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, PackageProduct pack) {
                Intent intent = new Intent(getContext(), ProductDetailActivity.class);
                //intent.putExtra("ff",pack);
                intent.putExtra(ProductDetailActivity.EXTRA_ID,pack.get_id());
                //--TODO property하면서 지움
//                intent.putExtra(ProductDetailActivity.EXTRA_MY_ID,my_id);
                /*intent.putExtra(ProductDetailActivity.EXTRA_Name, product.getProduct_Name());
                intent.putExtra(ProductDetailActivity.EXTRA_Count, product.getProduct_Count());
                intent.putExtra(ProductDetailActivity.EXTRA_Price, product.getProduct_Price());
                intent.putExtra(ProductDetailActivity.EXTRA_Picture, product.getProduct_Picture());
                intent.putExtra(ProductDetailActivity.EXTRA_Detail, product.getProduct_Detail());
                intent.putExtra(ProductDetailActivity.EXTRA_Detail2, product.getProduct_Detail2());
                intent.putExtra(ProductDetailActivity.EXTRA_Recipe, product.getProduct_Recipe());*/

                startActivity(intent);

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mClient.disconnect();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_salard_main, container, false);
        listView = (RecyclerView)view.findViewById(R.id.rv_list);
        listView.setAdapter(mAdapter);
        edittext = (EditText)view.findViewById(R.id.edit_location);
        imageOkay= (Button)view.findViewById(R.id.btn_ok);
        textView = (TextView)view.findViewById(R.id.text_location);
        mLayoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(mLayoutManager);
        imageLocation = (Button)view.findViewById(R.id.img_location);
        imageMap =(Button)view.findViewById(R.id.img_map);

        my_id = PropertyManager.getInstance().getId();
        imageMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageMap.setVisibility(View.INVISIBLE);
                imageOkay.setVisibility(View.VISIBLE);
                edittext.setVisibility(View.VISIBLE);
                textView.setVisibility(View.INVISIBLE);
            }
        });

            imageOkay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String keyword = edittext.getText().toString();
                    if (!TextUtils.isEmpty(keyword)) {
                        NetworkManager.getInstance().getSalardSearch(this, keyword, PropertyManager.getInstance().getId(), new NetworkManager.OnResultListener<PackageProductResult>() {
                            @Override
                            public void onSuccess(Request request, PackageProductResult result) {
                                mAdapter.clear();
                                mAdapter.addAll(result._package);

                            }

                            @Override
                            public void onFail(Request request, IOException exception) {

                            }
                        });

                    }
                }

            });

//        btn.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"NotoSansKR-Regular.otf"));

        init();
//        btn.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"NotoSansKR-Bold.otf"));

        imageLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageMap.setVisibility(View.VISIBLE);
                imageOkay.setVisibility(View.INVISIBLE);
                edittext.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.VISIBLE);
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                location = LocationServices.FusedLocationApi.getLastLocation(mClient);
                PropertyManager.getInstance().getMember().setMem_xloca(location.getLatitude());
                PropertyManager.getInstance().getMember().setMem_yloca(location.getLongitude());
                NetworkManager.getInstance().getTMapReverseGeocoding(this, location.getLatitude(),location.getLongitude(), new NetworkManager.OnResultListener<AddressInfo>() {
                    @Override
                    public void onSuccess(Request request, AddressInfo result) {
                        textView.setText(result.city_do+" "+result.gu_gun+" "+result.legalDong);
                        PropertyManager.getInstance().setDong(result.legalDong);
                    }

                    @Override
                    public void onFail(Request request, IOException exception) {

                    }
                });
            }
        });

        return view;
    }


    public void onResume(){
        super.onResume();
        init();
    }

    private void init() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        location = LocationServices.FusedLocationApi.getLastLocation(mClient);
//       String xloca = Double.toString(location.getLatitude());
//       String yloca =  Double.toString(location.getLongitude());
//        String xloca = "100";
//        String yloca = "200";

        if(Double.toString(PropertyManager.getInstance().getMember().getMem_xloca())!=null) {
            xloca = Double.toString(PropertyManager.getInstance().getMember().getMem_xloca());
            yloca = Double.toString(PropertyManager.getInstance().getMember().getMem_yloca());
        }
        else{
            xloca = "37.4670231";
            yloca = "126.957591";
        }
        if(PropertyManager.getInstance().getDong()==null){
            dong = "";
        }
        else
            dong = PropertyManager.getInstance().getDong();
        NetworkManager.getInstance().getHomeProductList(getContext(), xloca, yloca, my_id, dong,new NetworkManager.OnResultListener<PackageProductResult>() {
                    @Override
                    public void onSuccess(Request request,PackageProductResult result) {
//                        mHandler.sendMessage(mHandler.obtainMessage(1, result));
                        mAdapter.clear();
                        mAdapter.addAll(result._package);
//                        member = result.member;
                        //PropertyManager.getInstance().setMember(result.member);
                        //mAdapter.clear();
                        //mAdapter.addAll(result);


                    }

                    @Override
                    public void onFail(Request request, IOException exception) {
                        Toast.makeText(getContext(),"불러올 수 없습니다.",Toast.LENGTH_SHORT).show();
                    }
                });

/*
        mAdapter.clear();
        for(int i =0; i<10; i++){
            Product product = new Product();
            product.setProduct_Name("음식"+i);
            product.setProduct_Price(i*1000);
            product.setProduct_Count(i);
            product.setProduct_Picture(img[i]);
            product.setProduct_Detail("닭가슴살 100g, 발사믹 소스 1개, 달걀"+i+"개");
            product.setProduct_Detail2("소금, 후추, 간장, 물"+i+"5컵");
            product.setProduct_Recipe("1. 레시피를 본다. " + "\n2. 그대로 만든다." + "\n3. 먹는다.");
            if(i % 2 ==0){
                product.product_Soldout = true;
            }
            else
                product.product_Soldout = false;
            product.setProduct_Mem_Id(i);
            mAdapter.add(product);

        }*/
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mClient.disconnect();
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }



}
