package com.ghosthawk.salard.Map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Request;

public class MapActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnCameraChangeListener,
        GoogleMap.OnMapLongClickListener{

    GoogleApiClient mClient;
    TextView messageView;
    TextView infoView;
    ListView listView;
    ArrayAdapter<POI> mAdapter;
    Map<Marker, POI> poiResolver = new HashMap<>();
    Map<POI, Marker> markerResolver = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        messageView = (TextView) findViewById(R.id.text_message);
        Button btn=(Button)findViewById(R.id.btn_location);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listView.getVisibility()== View.GONE) {
                    listView.setVisibility(View.VISIBLE);
                    Animation anim = AnimationUtils.loadAnimation(MapActivity.this, R.anim.slide_left_in);
                    listView.startAnimation(anim);
                } else {
                    listView.setVisibility(View.GONE);
                    Animation anim = AnimationUtils.loadAnimation(MapActivity.this, R.anim.slide_left_out);
                    anim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            listView.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    listView.startAnimation(anim);
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
                }
            }
        });
        infoView = (TextView)findViewById(R.id.text_info);
        listView = (ListView)findViewById(R.id.listView);
        mAdapter = new ArrayAdapter<POI>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                POI poi = (POI)listView.getItemAtPosition(position);
                Marker m = markerResolver.get(poi);
                animateMap(m);
            }
        });
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            PoiSearchDialogFragment f = new PoiSearchDialogFragment();
            f.setOnPoiSearchResultCallback(new PoiSearchDialogFragment.OnPoiSearchResultCallback() {
                @Override
                public void onPoiSearchResult(SearchPOIInfo info) {
                    boolean isFirst = true;
                    clearAll();
                    for (POI poi : info.pois.poiList) {
                        if (isFirst) {
                            moveMap(poi.getLatitude(), poi.getLongitude(), 15f);
                            isFirst = false;
                        }
                        addMarker(poi);
                    }
                }
            });
            f.show(getSupportFragmentManager(), "searchdialog");
            return true;
        }
        if (id == android.R.id.home) {
            if (listView.getVisibility()== View.GONE) {
                listView.setVisibility(View.VISIBLE);
                Animation anim = AnimationUtils.loadAnimation(this, R.anim.slide_left_in);
                listView.startAnimation(anim);
            } else {
                listView.setVisibility(View.GONE);
                Animation anim = AnimationUtils.loadAnimation(this, R.anim.slide_left_out);
                anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        listView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                listView.startAnimation(anim);
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
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearAll() {
        Set<Marker> marker = poiResolver.keySet();
        for (Marker m : marker) {
            m.remove();
        }
        mAdapter.clear();
        poiResolver.clear();
        markerResolver.clear();
    }

    public void addMarker(POI poi) {
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(poi.getLatitude(), poi.getLongitude()));
        options.icon(BitmapDescriptorFactory.defaultMarker());
        options.anchor(0.5f, 1);
        options.title(poi.name);
        options.snippet(poi.getAddress());
        Marker marker = mMap.addMarker(options);
        mAdapter.add(poi);

        markerResolver.put(poi, marker);
        poiResolver.put(marker, poi);
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
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnCameraChangeListener(this);

    }
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        VisibleRegion region = mMap.getProjection().getVisibleRegion();
    }
    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, "marker : " + marker.getTitle(), Toast.LENGTH_SHORT).show();
        marker.hideInfoWindow();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        infoView.setText(marker.getTitle() + "\n\r" + marker.getSnippet());
        marker.showInfoWindow();
        return true;
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        addMarker(latLng.latitude, latLng.longitude);
    }

    private void addMarker(double lat, double lng) {
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(lat, lng));
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        options.anchor(0.5f, 1f);
        options.title("MyMarker");
        options.snippet("marker description");
        options.draggable(true);
        Marker m = mMap.addMarker(options);
    }
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
            // messageView.setText("lat : " + location.getLatitude() + ", lng : " + location.getLongitude());
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
