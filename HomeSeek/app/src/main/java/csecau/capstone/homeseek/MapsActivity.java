package csecau.capstone.homeseek;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private ClusterManager<Map_MarkerItem> mClusterManager;

    Marker selectedMarker;
    View marker_root_view;
    TextView tv_marker;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
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
        LatLng CAU = new LatLng(CAU_LAT, CAU_LON);//중앙대학교 서울캠퍼스

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
        mClusterManager.cluster();

    }

    private  void addMarkerItems() {
        mClusterManager.addItem(new Map_MarkerItem(37.505478,126.956304, 415000));
        mClusterManager.addItem(new Map_MarkerItem(37.507144,126.958268, 500000));
        mClusterManager.addItem(new Map_MarkerItem(37.507204,126.957972, 450000));
        mClusterManager.addItem(new Map_MarkerItem(37.505629,126.955763, 300000));
        mClusterManager.addItem(new Map_MarkerItem(37.504824,126.953263, 550000));
        mClusterManager.addItem(new Map_MarkerItem(37.505731,126.955748, 315000));
        mClusterManager.addItem(new Map_MarkerItem(37.505909,126.955229, 420000));
        mClusterManager.addItem(new Map_MarkerItem(37.505440,126.955112, 350000));
        mClusterManager.addItem(new Map_MarkerItem(37.505191,126.953415, 330000));
        mClusterManager.addItem(new Map_MarkerItem(37.504415,126.953028, 500000));
        mClusterManager.addItem(new Map_MarkerItem(37.504543,126.952320, 320000));
        mClusterManager.addItem(new Map_MarkerItem(37.504159,126.952320, 600000));
        mClusterManager.addItem(new Map_MarkerItem(37.506447,126.956656, 250000));
        mClusterManager.addItem(new Map_MarkerItem(37.507335,126.958384, 440000));
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


    private void getSampleMarkerItems() {
        ArrayList<Map_MarkerItem> sampleList = new ArrayList();


        sampleList.add(new Map_MarkerItem(37.505478,126.956304, 415000));
        sampleList.add(new Map_MarkerItem(37.507144,126.958268, 500000));
        sampleList.add(new Map_MarkerItem(37.507204,126.957972, 450000));
        sampleList.add(new Map_MarkerItem(37.505629,126.955763, 300000));
        sampleList.add(new Map_MarkerItem(37.504824,126.953263, 550000));
        sampleList.add(new Map_MarkerItem(37.505731,126.955748, 315000));
        sampleList.add(new Map_MarkerItem(37.505909,126.955229, 420000));
        sampleList.add(new Map_MarkerItem(37.505440,126.955112, 350000));
        sampleList.add(new Map_MarkerItem(37.505191,126.953415, 330000));
        sampleList.add(new Map_MarkerItem(37.504415,126.953028, 500000));
        sampleList.add(new Map_MarkerItem(37.504543,126.952320, 320000));
        sampleList.add(new Map_MarkerItem(37.504159,126.952320, 600000));
        sampleList.add(new Map_MarkerItem(37.506447,126.956656, 250000));
        sampleList.add(new Map_MarkerItem(37.507335,126.958384, 440000));
        //나중에 db에서 갔고 오는 형식으로 바꿔야됨


        for (Map_MarkerItem markerItem : sampleList) {
            addMarker(markerItem, false);
        }

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




    // View를 Bitmap으로 변환
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
        Map_MarkerItem temp = new Map_MarkerItem(lat, lon, price);
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
