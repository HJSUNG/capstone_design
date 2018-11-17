package csecau.capstone.homeseek;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

public class TmapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmap);

        LinearLayout linearLayoutTmap = (LinearLayout) findViewById(R.id.TmapLayout);
        final TMapView tmapview = new TMapView(this);

        tmapview.setSKTMapApiKey("d1b4a7e8-4afc-48ee-80a1-157777fbee58");
        tmapview.setLanguage(TMapView.LANGUAGE_KOREAN);
        tmapview.setIconVisibility(false);
        tmapview.setMapType(TMapView.MAPTYPE_STANDARD);
        tmapview.setZoomLevel(15);
        tmapview.setCenterPoint(126.9572649, 37.5039255);
        tmapview.setLocationPoint(126.9572649, 37.5039255);
        tmapview.setCompassMode(false);
        tmapview.setTrackingMode(true);

        linearLayoutTmap.addView(tmapview);

        TMapPoint CAU_point = new TMapPoint(37.50423882, 126.95685809);         //중앙대학교
        TMapPoint target_point = new TMapPoint(37.50884952, 126.96374622);      //흑석역

        TMapMarkerItem CAU_marker = new TMapMarkerItem();
        CAU_marker.setTMapPoint(CAU_point);
        CAU_marker.setName("중앙대학교");
        tmapview.addMarkerItem("중앙대학교", CAU_marker);

        TMapMarkerItem station_marker = new TMapMarkerItem();
        station_marker.setTMapPoint(target_point);
        station_marker.setName("흑석역");
        tmapview.addMarkerItem("흑석역", station_marker);

        TMapData tMapData = new TMapData();

        tMapData.findPathDataWithType(TMapData.TMapPathType.PEDESTRIAN_PATH, CAU_point, target_point, new TMapData.FindPathDataListenerCallback() {
            @Override
            public void onFindPathData(TMapPolyLine tMapPolyLine) {
                tmapview.addTMapPath(tMapPolyLine);
            }
        });

        // Function for Getting target's lat & lon
        /*final TMapData tmapdata = new TMapData();
        tmapdata.findAllPOI("흑석역", new TMapData.FindAllPOIListenerCallback() {
            @Override
            public void onFindAllPOI(ArrayList poiItem) {
                for (int i = 0; i < poiItem.size(); i++) {
                    TMapPOIItem item = (TMapPOIItem) poiItem.get(i);
                    Log.d("POI Name: ", item.getPOIName().toString() + ", " +
                            "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                            "Point: " + item.getPOIPoint().toString());
                }

            }
        });*/
    }
}