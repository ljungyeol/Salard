package com.ghosthawk.salard.Map;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ghosthawk.salard.Data.MapResult;
import com.ghosthawk.salard.Data.MapResultResult;
import com.ghosthawk.salard.Data.PackageProduct;
import com.ghosthawk.salard.Manager.NetworkManager;
import com.ghosthawk.salard.R;
import com.ghosthawk.salard.Sell.ProductDetailActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Request;

public class MapActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnCameraChangeListener{

    GoogleApiClient mClient;
    TextView messageView,messageView2;
   // TextView infoView;
    EditText editMessage;
    TextView textName, textPrice,textLocation;
    ImageView imageMem, imageMain, imageRank;
    Location location;
    Button btn2;
    View view;
    int packid;
    Map<Marker, PackageProduct> poiResolver = new HashMap<>();
    Map<PackageProduct, Marker> markerResolver = new HashMap<>();
    //List<PackageProduct> packageproduct = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        if (Build.VERSION.SDK_INT >= 21) {   //상태바 색
            getWindow().setStatusBarColor(Color.parseColor("#00d076"));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Salard");
        messageView = (TextView) findViewById(R.id.text_message);
        messageView2 = (TextView)findViewById(R.id.text_message1);
        Button btn=(Button)findViewById(R.id.btn_location);
        btn2=(Button)findViewById(R.id.btn_map);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageView.setVisibility(View.VISIBLE);
//                messageView2.setVisibility(View.VISIBLE);
                editMessage.setVisibility(View.GONE);
                if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                location = LocationServices.FusedLocationApi.getLastLocation(mClient);

                displayMessage(location);
                LocationRequest request = new LocationRequest();
                request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                LocationServices.FusedLocationApi.requestLocationUpdates(mClient, request, mListener);

            }

//                ViewPropertyAnimatorCompat animator = ViewCompat.animate(listView);
//                animator.translationX(-listView.getMeasuredWidth());
//                animator.setListener(new ViewPropertyAnimatorListener() {
//                    @Override
//                    public void onAnimationStart(View view) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(View view) {
//                        listView.setVisibility(View.GONE);
//                    }
//
//                    @Override
//                    public void onAnimationCancel(View view) {
//
//                    }
//                });
//                animator.start();


        });
      //  infoView = (TextView)findViewById(R.id.text_info);
        editMessage = (EditText)findViewById(R.id.edit_message);
        textName = (TextView)findViewById(R.id.text_name);
        textPrice = (TextView)findViewById(R.id.text_price);
        textLocation = (TextView)findViewById(R.id.text_location);
        imageMain = (ImageView)findViewById(R.id.img_main);
        imageMem = (ImageView)findViewById(R.id.img_mem);
        imageRank = (ImageView)findViewById(R.id.img_rank);
        view = (View)findViewById(R.id.view_map_window);
        view.setVisibility(View.INVISIBLE);
        mClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
//                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        fragment.getMapAsync(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.selector_action_back);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sell_home, menu);
        return true;
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
    protected void onResume() {
        super.onResume();
        NetworkManager.getInstance().getMap(new NetworkManager.OnResultListener<MapResult>() {
            @Override
            public void onSuccess(Request request,MapResult result) {
                for(int i =0 ; i<result.packageproduct.size();i++){
                    //packageproduct.add(result.packageproduct.get(i));
                    addMarker(result.packageproduct.get(i));
                   // addMarker(packageproduct.get(i).getPackage_xloca(), packageproduct.get(i).getPackage_yloca());
                }
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        if (id == R.id.action_search) {
            editMessage.setVisibility(View.VISIBLE);
            messageView.setVisibility(View.INVISIBLE);
//            messageView2.setVisibility(View.INVISIBLE);
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String keyword = editMessage.getText().toString();
                    editMessage.setText("");
                    if(!TextUtils.isEmpty(keyword)){
                        try {
                            NetworkManager.getInstance().getTMapSearchPOI(this, keyword, new NetworkManager.OnResultListener<SearchPOIInfo>() {
                                @Override
                                public void onSuccess(Request request, SearchPOIInfo result) {
                                    boolean isFirst = true;
                                    for (POI poi: result.pois.poiList) {
                                        if (isFirst) {
                                            moveMap(poi.getLatitude(), poi.getLongitude(), 15f);
                                            break;
                                        }

                                    }
                                }

                                @Override
                                public void onFail(Request request, IOException exception) {
                                    Toast.makeText(MapActivity.this, "fail", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearAll() {
        Set<Marker> marker = poiResolver.keySet();
        for (Marker m : marker) {
            m.remove();
        }
        poiResolver.clear();
        markerResolver.clear();
    }

    public void addMarker(PackageProduct packageProduct) {
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(packageProduct.getPackage_xloca(), packageProduct.getPackage_yloca()));
        options.icon(BitmapDescriptorFactory.defaultMarker());
        options.anchor(0.5f, 1);
//        options.title(packageProduct.getPackage_name());
//        options.snippet(packageProduct.getPackage_price()+"원");
        Marker marker = mMap.addMarker(options);
        markerResolver.put(packageProduct, marker);
        poiResolver.put(marker, packageProduct);
    }

    GoogleMap mMap;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//        mMap.getUiSettings().setZoomControlsEnabled(true);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        mMap.setMyLocationEnabled(true);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnCameraChangeListener(this);
        //mMap.setInfoWindowAdapter(new MyInfoWindowAdapter(this, poiResolver));

    }
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        VisibleRegion region = mMap.getProjection().getVisibleRegion();
    }
    @Override
    public void onInfoWindowClick(Marker marker) {
//        Toast.makeText(this, marker.getTitle() + "   "+marker.getSnippet(), Toast.LENGTH_SHORT).show();
//        animateMap(marker);
//        marker.hideInfoWindow();
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        PackageProduct p = poiResolver.get(marker);
        packid = p.get_id();
        view.setVisibility(View.VISIBLE);
        textName.setText(p.getPackage_name());
        textPrice.setText(p.getPackage_price()+"원");
        Glide.with(imageMain.getContext()).load(p.getPackage_mainpicture()).into(imageMain);
        Glide.with(imageMem.getContext()).load(p.getPackage_personpicture()).into(imageMem);
        int img[]={
                R.drawable.rank0, R.drawable.rank1, R.drawable.rank2,
                R.drawable.rank3,R.drawable.rank4,R.drawable.rank5
        };
        int i = p.getPerson_sellcount();
        Location foodloca = new Location("a");

        foodloca.setLatitude(p.package_xloca);
        foodloca.setLongitude(p.package_yloca);

        textLocation.setText(p.getPackage_count()+"개 / "+foodloca.distanceTo(location)+"km");

        if(i>0 && i<10){
            Glide.with(imageRank.getContext()).load(img[1]).into(imageRank);
        }
        else if(i<30){
            Glide.with(imageRank.getContext()).load(img[2]).into(imageRank);
        }
        else if(i<50){
            Glide.with(imageRank.getContext()).load(img[3]).into(imageRank);
        }
        else if(i<80){
            Glide.with(imageRank.getContext()).load(img[4]).into(imageRank);
        }
        else if(i>=100){
            Glide.with(imageRank.getContext()).load(img[5]).into(imageRank);
        }
        else{

        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, ProductDetailActivity.class);
                //intent.putExtra("ff",pack);
                intent.putExtra(ProductDetailActivity.EXTRA_ID,packid);
                startActivity(intent);
            }
        });
//        imageMain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("aabbbbbbbbaaaaa","aaaaaaaaaa");
//                Intent intent = new Intent(MapActivity.this, ProductDetailActivity.class);
//                //intent.putExtra("ff",pack);
//                intent.putExtra(ProductDetailActivity.EXTRA_ID,packid);
//
//            }
//        });

//        NetworkManager.getInstance().getTMapReverseGeocoding(this, p.getPackage_xloca(),p.getPackage_yloca(), new NetworkManager.OnResultListener<AddressInfo>() {
//            @Override
//            public void onSuccess(Request request, AddressInfo result) {
//                textLocation.setText(result.legalDong);
//
//            }
//
//            @Override
//            public void onFail(Request request, IOException exception) {
//
//            }
//        });

        marker.showInfoWindow();
        return true;
    }

//    @Override
//    public void onMapLongClick(LatLng latLng) {
//        addMarker(latLng.latitude, latLng.longitude);
//    }

    private void addMarker(double lat, double lng) {
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(lat, lng));
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        options.anchor(0.5f, 1f);
        options.title("MyMarker");
        options.snippet("lat : "+lat +"lng : "+lng);
        options.draggable(true);
        Marker m = mMap.addMarker(options);

    }

//    private void addMarker(PackageProduct packageproduct){
//        MarkerOptions options = new MarkerOptions();
//        options.position(new LatLng(packageproduct.getPackage_xloca(),packageproduct.getPackage_yloca()));
//        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//        options.anchor(0.5f, 1f);
//        options.title(packageproduct.getPackage_name());
//        options.snippet(packageproduct.getPackage_price()+"원");
//        options.draggable(true);
//        Marker m = mMap.addMarker(options);
//
//    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        location = LocationServices.FusedLocationApi.getLastLocation(mClient);
        displayMessage(location);
        LocationRequest request = new LocationRequest();
        //request.setInterval(10000); 위치 업데이트 되는 주기
       // request.setFastestInterval(5000); 위치 획득 후 업데이트 되는 주기
        request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(mClient, request, mListener);
    }

    LocationListener mListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            displayMessage(location);
        }
    };

    private void displayMessage(Location location) {
        if (location != null) {
                    //messageView.setText("lat : " + location.getLatitude() + ", lng : " + location.getLongitude());
                    NetworkManager.getInstance().getTMapReverseGeocoding(this, location.getLatitude(), location.getLongitude(), new NetworkManager.OnResultListener<AddressInfo>() {

                @Override
                public void onSuccess(Request request, AddressInfo result) {
                    messageView.setText(result.fullAddress);

                }

                @Override
                public void onFail(Request request, IOException exception) {

                }

            });
            moveMap(location.getLatitude(), location.getLongitude(), 15f);

        }
    }
    private void animateMap(final Marker marker) {
        CameraPosition position = new CameraPosition.Builder()
                .target(marker.getPosition())
                .zoom(15)
//                .bearing(30)
//                .tilt(30)
                .build();

//        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), zoom);
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);

        if (mMap != null) {
            mMap.animateCamera(update, 1000, new GoogleMap.CancelableCallback() {
                @Override
                public void onFinish() {
                    marker.showInfoWindow();
                }

                @Override
                public void onCancel() {

                }
            });
        }
    }

    private void moveMap(double lat, double lng, float zoom) {
        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(lat, lng))
                .zoom(zoom)
//                .bearing(30)
//                .tilt(30)
                .build();

//        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), zoom);
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);

        if (mMap != null) {
            mMap.moveCamera(update);
        }
    }




    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
