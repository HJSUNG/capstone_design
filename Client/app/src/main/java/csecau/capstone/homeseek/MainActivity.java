package csecau.capstone.homeseek;

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

    ListView listview = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] items = {"Menu1", "Menu2", "Menu3", "Menu4", "Menu5", "Menu6"};
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        listview = (ListView) findViewById(R.id.drawer_menulist);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                TextView contentTextview = (TextView) findViewById(R.id.drawer_content);

                switch (position) {
                    case 0: // menu1
                        contentTextview.setText("Menu1 Selected");
                        break;
                    case 1: // menu2
                        contentTextview.setText("Menu2 Selected");
                        break;
                    case 2: // menu3
                        contentTextview.setText("Menu3 Selected");
                        break;
                    case 3: // menu4
                        contentTextview.setText("Menu4 Selected");
                        break;
                    case 4: // menu5
                        contentTextview.setText("Menu5 Selected");
                        break;
                    case 5: //menu6
                        contentTextview.setText("Menu6 Selected");
                        break;
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
                drawer.closeDrawer(Gravity.LEFT);
            }
        });
    }
}