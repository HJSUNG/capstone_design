package csecau.capstone.homeseek;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

public class TmapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmap);

        LinearLayout linearLayoutTmap = (LinearLayout)findViewById(R.id.TmapLayout);
        TMapView tmapview = new TMapView(this);

        tmapview.setSKTMapApiKey("d1b4a7e8-4afc-48ee-80a1-157777fbee58");
        tmapview.setLanguage(TMapView.LANGUAGE_KOREAN);
        tmapview.setIconVisibility(true);
        tmapview.setMapType(TMapView.MAPTYPE_STANDARD);
        tmapview.setZoomLevel(10);
        tmapview.setCenterPoint(126.9572649,37.5039255);
        tmapview.setLocationPoint(126.9572649,37.5039255);
        tmapview.setCompassMode(false);
        tmapview.setTrackingMode(true);

        linearLayoutTmap.addView(tmapview);

        TMapMarkerItem CAU_marker = new TMapMarkerItem();
        TMapPoint CAU_point = new TMapPoint(37.5039255,126.9572649);

        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.tmap_marker);

        //CAU_marker.setPosition(0.5f, 1.0f);
        CAU_marker.setTMapPoint(CAU_point);
        CAU_marker.setName("중앙대학교");
        tmapview.addMarkerItem("중앙대학교",CAU_marker);

    }
}
