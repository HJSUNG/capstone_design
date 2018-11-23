package csecau.capstone.homeseek;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private ClusterManager<Map_MarkerItem> mClusterManager;
    private Map_MarkerItem clickedClusterItem;

    Marker selectedMarker;
    View marker_root_view;
    TextView tv_marker;
    private GoogleMap mMap;
    private ArrayList<posting_list> list_itemArrayList;
    private static final double DEFAULT_RADIUS = 1.0000001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        list_itemArrayList = new ArrayList<posting_list>();
        Bundle bundle = getIntent().getExtras();
        list_itemArrayList = bundle.getParcelableArrayList("marker_list");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mClusterManager = new ClusterManager<>(this, googleMap);
        MarkerOptions markerOptions = new MarkerOptions();

        double CAU_LAT = 37.5039255;
        double CAU_LON = 126.9572649;
        LatLng CAU = new LatLng(CAU_LAT, CAU_LON);

        markerOptions.position(CAU)
                .title("중앙대 서울캠퍼스")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mMap.addMarker(markerOptions);

        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CAU, 16));

        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setOnInfoWindowClickListener(mClusterManager);
        addMarkerItems();
        mClusterManager.setOnClusterItemInfoWindowClickListener(new ClusterManager.OnClusterItemInfoWindowClickListener<Map_MarkerItem>() {
            @Override
            public void onClusterItemInfoWindowClick(Map_MarkerItem map_markerItem) {

            }
        });
        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<Map_MarkerItem>() {
            @Override
            public boolean onClusterItemClick(Map_MarkerItem map_markerItem) {
                int index = map_markerItem.getIndex();
                Intent intent = new Intent(getApplicationContext(), PostingActivity.class);
                intent.putExtra("title", list_itemArrayList.get(index).getTitle());
                intent.putExtra("homeid",list_itemArrayList.get(index).getHomeid());
                intent.putExtra("address", list_itemArrayList.get(index).getAddress());
                intent.putExtra("detailAddress", list_itemArrayList.get(index).getDetailaddress());
                intent.putExtra("explain", list_itemArrayList.get(index).getExplain());
                intent.putExtra("deposit", list_itemArrayList.get(index).getDeposit());
                intent.putExtra("monthly", list_itemArrayList.get(index).getMonthly());
                intent.putExtra("term", list_itemArrayList.get(index).getTerm());

                intent.putExtra("washing", list_itemArrayList.get(index).getWashing());
                intent.putExtra("refrigerator", list_itemArrayList.get(index).getRefrigerator());
                intent.putExtra("desk", list_itemArrayList.get(index).getDesk());
                intent.putExtra("bed", list_itemArrayList.get(index).getBed());
                intent.putExtra("microwave", list_itemArrayList.get(index).getMicrowave());
                intent.putExtra("closet", list_itemArrayList.get(index).getCloset());

                intent.putExtra("imageone", list_itemArrayList.get(index).getImageone());
                intent.putExtra("imagetwo", list_itemArrayList.get(index).getImagetwo());
                intent.putExtra("imagethree", list_itemArrayList.get(index).getImagethree());

                intent.putExtra("phonenum", list_itemArrayList.get(index).getPhoneNum());
                startActivity(intent);
                return false;
            }
        });
        mClusterManager.cluster();
    }

    private void addMarkerItems() {
        Geocoder geocoder;
        for (int i = 0; i < list_itemArrayList.size(); i++) {
            geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addressList = null;
            try {
                addressList = geocoder.getFromLocationName(list_itemArrayList.get(i).getAddress(), 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int monthly = Integer.parseInt(list_itemArrayList.get(i).getMonthly());
            for(int j = 0; j < i; j++){
                if(compareAddress(addressList.get(0).getLatitude(), addressList.get(0).getLongitude(),list_itemArrayList.get(j).getAddress())){
                    addressList.get(0).setLatitude(addressList.get(0).getLatitude() * DEFAULT_RADIUS);
                    addressList.get(0).setLongitude(addressList.get(0).getLongitude() * DEFAULT_RADIUS);
                }
            }
            mClusterManager.addItem(new Map_MarkerItem(addressList.get(0).getLatitude(), addressList.get(0).getLongitude(), monthly, i));
            mClusterManager.cluster();
        }
    }

    private boolean compareAddress(double lat, double lon, String y_address){
        Geocoder geocoder;
        List<Address> addressList = null;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addressList = geocoder.getFromLocationName(y_address, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(lat == addressList.get(0).getLatitude()){
            if(lon == addressList.get(0).getLongitude()){
                return true;
            }
            return false;
        }
        return false;
    }

    private class RenderClusterInfoWindow extends DefaultClusterRenderer<Map_MarkerItem> {

        RenderClusterInfoWindow(Context context, GoogleMap map, ClusterManager<Map_MarkerItem> clusterManager) {
            super(context, map, clusterManager);
        }

        @Override
        protected void onClusterRendered(Cluster<Map_MarkerItem> cluster, Marker marker) {
            super.onClusterRendered(cluster, marker);
        }

        @Override
        protected void onBeforeClusterItemRendered(Map_MarkerItem item, MarkerOptions markerOptions) {
            markerOptions.title(Integer.toString(item.getPrice()));

            super.onBeforeClusterItemRendered(item, markerOptions);
        }
    }

    private void setCustomMarkerView() {

        marker_root_view = LayoutInflater.from(this).inflate(R.layout.marker_layout, null);
        tv_marker = (TextView) marker_root_view.findViewById(R.id.tv_marker);
    }

    private Marker addMarker(Map_MarkerItem markerItem, boolean isSelectedMarker) {


        LatLng position = markerItem.getPosition();
        int price = markerItem.getPrice();
        String formatted = NumberFormat.getCurrencyInstance().format((price));

        tv_marker.setText(formatted);

        if (isSelectedMarker) {
            tv_marker.setBackgroundResource(R.drawable.ic_marker_phone_blue);
            tv_marker.setTextColor(Color.WHITE);
        } else {
            tv_marker.setBackgroundResource(R.drawable.ic_marker_phone);
            tv_marker.setTextColor(Color.BLACK);
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(Integer.toString(price));
        markerOptions.position(position);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker_root_view)));

        return mMap.addMarker(markerOptions);
    }

    private Bitmap createDrawableFromView(Context context, View view) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    private Marker addMarker(Marker marker, boolean isSelectedMarker) {
        double lat = marker.getPosition().latitude;
        double lon = marker.getPosition().longitude;
        int price = Integer.parseInt(marker.getTitle());
        Map_MarkerItem temp = new Map_MarkerItem(lat, lon, price, price);
        return addMarker(temp, isSelectedMarker);

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        CameraUpdate center = CameraUpdateFactory.newLatLng(marker.getPosition());
        mMap.animateCamera(center);

        changeSelectedMarker(marker);

        return true;
    }

    private void changeSelectedMarker(Marker marker) {
        // 선택했던 마커 되돌리기
        if (selectedMarker != null) {
            addMarker(selectedMarker, false);
            selectedMarker.remove();
        }

        // 선택한 마커 표시
        if (marker != null) {
            selectedMarker = addMarker(marker, true);
            marker.remove();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        changeSelectedMarker(null);
    }
}
