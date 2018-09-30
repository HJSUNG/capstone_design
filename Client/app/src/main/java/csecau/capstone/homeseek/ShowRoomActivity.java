package csecau.capstone.homeseek;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowRoomActivity extends AppCompatActivity {
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager =(ViewPager)findViewById(R.id.pager);
        CustomAdapter adapter = new CustomAdapter(getLayoutInflater());
        pager.setAdapter(adapter);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        TextView textView = (TextView)findViewById(R.id.textTitle);
        TextView textView2 = (TextView)findViewById(R.id.text);
        TextView textView3 = (TextView)findViewById(R.id.text2);
        textView.setText("방 급매합니다");
        textView2.setText("---------------------------------");
        textView3.setText("장소 : 서울특별시 동작구\n월세:40만원\n보증금:1천만원");
        BottomNavigationView navigationView =(BottomNavigationView)findViewById(R.id.main_bar);

        BottomNavigationView.OnNavigationItemSelectedListener navigationListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.call:
                        Toast.makeText(getApplicationContext(), "Call", Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.favorite:
                        Toast.makeText(getApplicationContext(), "Favorites", Toast.LENGTH_LONG).show();
                        return true;
                }
                return false;
            }
        };
        navigationView.setOnNavigationItemSelectedListener(navigationListener);
    }
}
