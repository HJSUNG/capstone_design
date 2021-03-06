package csecau.capstone.homeseek;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class TmapActivity extends AppCompatActivity {

    public static String total_time = "1";
    public static String total_distance = "2";

    TextView total_time_textview;
    TextView total_distance_textview;

    public void set_total_time (String input) {
        total_time_textview.setText(input);
    }

    public void set_total_distance (String input) {
        total_distance_textview.setText(input);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Double latitude_from_posting = intent.getExtras().getDouble("latitude");
        Double longitude_from_posting = intent.getExtras().getDouble("longitude");
        setContentView(R.layout.activity_tmap);

        total_time_textview = (TextView)findViewById(R.id.total_time);
        total_distance_textview = (TextView)findViewById(R.id.total_distance);

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

        TMapData tmapdata = new TMapData();

        TMapPoint CAU_point = new TMapPoint(37.50423882, 126.95685809);         //중앙대학교
//        String CAU_lat = "37.50423882";
//        String CAU_lon = "126.95685809";
        TMapPoint target_point = new TMapPoint(latitude_from_posting, longitude_from_posting);      //흑석역
//        String target_lat = "37.50884952";
//        String target_lon = "126.96374622";

        TMapMarkerItem CAU_marker = new TMapMarkerItem();
        CAU_marker.setTMapPoint(CAU_point);
        CAU_marker.setName("중앙대학교");
        tmapview.addMarkerItem("중앙대학교", CAU_marker);

        TMapMarkerItem target_marker = new TMapMarkerItem();
        target_marker.setTMapPoint(target_point);
        target_marker.setName("매물");
        tmapview.addMarkerItem("매물", target_marker);

        TMapData tMapData = new TMapData();

        tMapData.findPathDataWithType(TMapData.TMapPathType.PEDESTRIAN_PATH, CAU_point, target_point, new TMapData.FindPathDataListenerCallback() {
            @Override
            public void onFindPathData(TMapPolyLine tMapPolyLine) {
                tmapview.addTMapPath(tMapPolyLine);
            }
        });


        tmapdata.findPathDataAllType(TMapData.TMapPathType.PEDESTRIAN_PATH, CAU_point, target_point, new TMapData.FindPathDataAllListenerCallback() {
            @Override
            public void onFindPathDataAll(Document document) {
                Element root = document.getDocumentElement();
                NodeList nodeListTotalTime = root.getElementsByTagName("tmap:totalTime");

                for (int i = 0; i < nodeListTotalTime.getLength(); i++) {
                    NodeList nodeListTotalTimeItem = nodeListTotalTime.item(i).getChildNodes();
                    for (int j = 0; j < nodeListTotalTimeItem.getLength(); j++) {
                        if (true) {
                            Log.d("###", nodeListTotalTimeItem.item(j).getTextContent().trim());

                            int time = Integer.parseInt(nodeListTotalTimeItem.item(j).getTextContent().trim());
                            int time_quotient, time_remainder;

                            time_quotient = time/60;
                            time_remainder = time%60;

                            if(time_remainder>30) {
                                time_quotient +=1;
                            }

                            set_total_time("총 소요 시간 : 약 " + time_quotient+ " 분");
                            Log.d("@@@", total_time);
                        }
                    }
                }

                NodeList nodeListTotalDistance = root.getElementsByTagName("tmap:totalDistance");

                for (int i = 0; i < nodeListTotalDistance.getLength(); i++) {
                    NodeList nodeListTotalDistanceItem = nodeListTotalDistance.item(i).getChildNodes();
                    for (int j = 0; j < nodeListTotalDistanceItem.getLength(); j++) {
                        if (true) {
                            Log.d("###", nodeListTotalDistanceItem.item(j).getTextContent().trim());

                            int distance = Integer.parseInt(nodeListTotalDistanceItem.item(j).getTextContent().trim());
                            int distance_quotient, distance_remainder;
                            int distance_remainder_quotient, distance_remainder_remainder;
                            double distance_quotient_double = 0, distance_remainder_quotient_double =0;

                            if(distance >= 1000) {
                                distance_quotient = distance / 1000;
                                distance_remainder = distance % 1000;

                                distance_quotient_double +=distance_quotient;

                                distance_remainder_quotient = distance_remainder / 100;
                                distance_remainder_quotient_double = distance_remainder_quotient;
                                distance_quotient_double += distance_remainder_quotient_double / 10;

                                distance_remainder_remainder = distance_remainder % 100;

                                if (distance_remainder_remainder > 50) {
                                    distance_quotient_double += 0.1;
                                }

                                set_total_distance("총 거리 : 약 " + distance_quotient_double + " km");
                            } else {
                                distance_quotient = distance / 10;
                                distance_quotient = distance_quotient * 10;

                                set_total_distance("총 거리 : 약 " + distance_quotient + " m");
                            }
                            Log.d("@@@", total_distance);
                        }
                    }
                }
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