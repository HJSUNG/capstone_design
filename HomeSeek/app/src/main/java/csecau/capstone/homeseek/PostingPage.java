package csecau.capstone.homeseek;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PostingPage extends AppCompatActivity {
    TextView titleView, contentView, depositView, monthlyView, termView;
    ImageView imageone, imagetwo, imagethree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);
        titleView = (TextView)findViewById(R.id.titleWrite);
        contentView = (TextView)findViewById(R.id.contents);
        imageone = (ImageView)findViewById(R.id.image1);
        imagetwo = (ImageView)findViewById(R.id.image2);
        imagethree = (ImageView)findViewById(R.id.image3);
        depositView = (TextView)findViewById(R.id.depositview);
        monthlyView = (TextView)findViewById(R.id.monthlyview);
        termView = (TextView)findViewById(R.id.termview);


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