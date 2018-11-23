package csecau.capstone.homeseek;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class TmapActivity extends AppCompatActivity {

    public static int totalTime;
    public static int totalDistance;
    public static String TAG = "T-map test";

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
        String CAU_lat = "37.50423882";
        String CAU_lon = "126.95685809";
        TMapPoint target_point = new TMapPoint(37.50884952, 126.96374622);      //흑석역
        String target_lat = "37.50884952";
        String target_lon = "126.96374622";

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

        Request_information task = new Request_information();
        task.execute();

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

    class Request_information extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(TmapActivity.this, result, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String...params) {
            String ID = (String)params[1];
            String item_num = (String)params[2];

            String serverURL = (String)params[0];
            String postParameters = "ID=" + ID;

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString();
            } catch (Exception e) {
                Log.d(TAG, "Login Error ", e);
                return new String("ERROR: " + e.getMessage());
            }
        }
    }

}