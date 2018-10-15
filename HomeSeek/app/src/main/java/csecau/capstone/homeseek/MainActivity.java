package csecau.capstone.homeseek;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] items = {"Log-In", "Search", "Board", "PictureList","Maps"};
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        listview = (ListView) findViewById(R.id.drawer_menulist);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                TextView contentTextview = (TextView) findViewById(R.id.drawer_content);

                switch (position) {
                    case 0: // menu1
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        break;
                    case 1: // menu2
                        Intent intent1 = new Intent(getApplicationContext(), SearchActivity.class);
                        startActivity(intent1);
                        break;
                    case 2: // menu3
                        Intent intent2 = new Intent(getApplicationContext(), BoardActivity.class);
                        startActivity(intent2);
                        break;
                    case 3: // menu4
                        Intent intent3 = new Intent(getApplicationContext(), Picture_List_Activity.class);
                        startActivity(intent3);
                        break;
                    case 4: // menu5
                        Intent intent4 = new Intent(getApplicationContext(), MapsActivity.class);
                        startActivity(intent4);
                        break;
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
                drawer.closeDrawer(Gravity.LEFT);
            }
        });
    }
}