package csecau.capstone.homeseek;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.skt.Tmap.TMapView;

public class TmapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmap);

        LinearLayout linearLayoutTmap = (LinearLayout)findViewById(R.id.TmapLayout);
        TMapView tMapView = new TMapView(this);

        tMapView.setSKTMapApiKey("d1b4a7e8-4afc-48ee-80a1-157777fbee58");
        linearLayoutTmap.addView(tMapView);
    }
}
