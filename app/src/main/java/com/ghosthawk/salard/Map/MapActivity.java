package com.ghosthawk.salard.Map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    TextView messageView;
   // TextView infoView;
    TextView textName, textPrice,textLocation;
    ImageView imageMem, imageMain;

    Map<Marker, PackageProduct> poiResolver = new HashMap<>();
    Map<PackageProduct, Marker> markerResolver = new HashMap<>();
    //List<PackageProduct> packageproduct = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        messageView = (TextView) findViewById(R.id.text_message);
        Button btn=(Button)findViewById(R.id.btn_location);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Location location = LocationServices.FusedLocationApi.getLastLocation(mClient);
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
        textName = (TextView)findViewById(R.id.text_name);
        textPrice = (TextView)findViewById(R.id.text_price);
        textLocation = (TextView)findViewById(R.id.text_location);
        imageMain = (ImageView)findViewById(R.id.img_main);
        imageMem = (ImageView)findViewById(R.id.img_mem);

        mClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .build();
        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        fragment.getMapAsync(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_dialog_map);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sell_home, menu);
        return true;
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
        if (id == R.id.action_search) {
            PoiSearchDialogFragment f = new PoiSearchDialogFragment();
            f.setOnPoiSearchResultCallback(new PoiSearchDialogFragment.OnPoiSearchResultCallback() {
                @Override
                public void onPoiSearchResult(SearchPOIInfo info) {
                    boolean isFirst = true;
                    clearAll();
                    for (PackageProduct packageProduct : info.pois.packageProducts) {
                        if (isFirst) {
                            moveMap(packageProduct.getPackage_xloca(), packageProduct.getPackage_yloca(), 15f);
                            isFirst = false;
                        }
                        addMarker(packageProduct);
                    }
                }
            });
            f.show(getSupportFragmentManager(), "searchdialog");
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
        options.title(packageProduct.getPackage_name());
        options.snippet(packageProduct.getPackage_price()+"원");
        Marker marker = mMap.addMarker(options);
        markerResolver.put(packageProduct, marker);
        poiResolver.put(marker, packageProduct);
    }

    GoogleMap mMap;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.getUiSettings().setZoomControlsEnabled(true);
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
        Toast.makeText(this, marker.getTitle() + "   "+marker.getSnippet(), Toast.LENGTH_SHORT).show();
        animateMap(marker);
        marker.hideInfoWindow();
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
//        textName.setText(marker.getTitle());
        PackageProduct p = poiResolver.get(marker);
        textName.setText(p.getPackage_name());
        textPrice.setText(p.getPackage_price()+"원");
        Glide.with(imageMain.getContext()).load(p.getPackage_mainpicture()).into(imageMain);
        Glide.with(imageMem.getContext()).load(p.getPackage_personpicture()).into(imageMem);
        NetworkManager.getInstance().getTMapReverseGeocoding(this, p.getPackage_xloca(),p.getPackage_yloca(), new NetworkManager.OnResultListener<AddressInfo>() {
            @Override
            public void onSuccess(Request request, AddressInfo result) {
                textLocation.setText(result.legalDong);

            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });

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
        Location location = LocationServices.FusedLocationApi.getLastLocation(mClient);
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
