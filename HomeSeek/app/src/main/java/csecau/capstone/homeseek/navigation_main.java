package csecau.capstone.homeseek;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static csecau.capstone.homeseek.MainActivity.user;

public class navigation_main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static TextView ID_textview;
    public static TextView nickname_textview;
    public static TextView phone_textview;
    public static TextView type_textview;

    TextView user_id, user_nickname, user_phone, user_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        setContentView(R.layout.nav_header_main);

//        ID_textview = (TextView) findViewById(R.id.user_ID);
//        nickname_textview = (TextView)findViewById(R.id.user_nickname);
//        phone_textview = (TextView)findViewById(R.id.user_phone);
//        type_textview = (TextView)findViewById(R.id.user_type);
//
//        ID_textview.setText(user.info_ID);
//        nickname_textview.setText(user.info_nickname);
//        phone_textview.setText(user.info_phone);
//        type_textview.setText(user.info_type);

        setContentView(R.layout.activity_main_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        ID_textview = (TextView) findViewById(R.id.user_information_ID);
//        nickname_textview = (TextView)findViewById(R.id.user_information_nickname);
//        phone_textview = (TextView)findViewById(R.id.user_information_phone);
//        type_textview = (TextView)findViewById(R.id.user_information_type);

//        ID_textview.setText((CharSequence) user.info_ID);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        user_id= (TextView)findViewById(R.id.user_ID);

        View nav_header_main = navigationView.getHeaderView(0);

        TextView nav_header_nickname = (TextView) nav_header_main.findViewById(R.id.user_nav_nickname);
        TextView nav_header_id = (TextView) nav_header_main.findViewById(R.id.user_nav_ID);
        TextView nav_header_phone = (TextView) nav_header_main.findViewById(R.id.user_nav_phone);
        TextView nav_header_type = (TextView) nav_header_main.findViewById(R.id.user_nav_type);

        nav_header_nickname.setText(user.info_nickname);
        nav_header_id.setText(user.info_ID);
        nav_header_phone.setText(user.info_phone);
        nav_header_type.setText(user.info_type);

//        setContentView(R.layout.nav_header_main);
//
//        ID_textview = (TextView) findViewById(R.id.user_ID);
//        nickname_textview = (TextView)findViewById(R.id.user_nickname);
//        phone_textview = (TextView)findViewById(R.id.user_phone);
//        type_textview = (TextView)findViewById(R.id.user_type);
//
//        ID_textview.setText((CharSequence) user.info_ID);
//        nickname_textview.setText((CharSequence) user.info_nickname);
//        phone_textview.setText((CharSequence) user.info_phone);
//        type_textview.setText((CharSequence) user.info_type);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    상단우측 기능
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_close) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            user.log_out();
            Intent intent0 = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent0);
            finish();
        } else if (id == R.id.nav_search) {
            Intent intent1 = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent1);
        } else if (id == R.id.nav_board) {
            Intent intent2 = new Intent(getApplicationContext(), BoardShow.class);
            startActivity(intent2);
//        } else if (id == R.id.nav_picturelist) {
//            Intent intent3 = new Intent(getApplicationContext(), Picture_List_Activity.class);
//            startActivity(intent3);
//        } else if (id == R.id.nav_maps) {
//            Intent intent4 = new Intent(getApplicationContext(), MapsActivity.class);
//            startActivity(intent4);
//        } else if (id == R.id.nav_tmap) {
//            Intent intent5 = new Intent(getApplicationContext(), TmapActivity.class);
//            startActivity(intent5);
        } else if (id == R.id.nav_upload) {
            if(user.info_type.equals("Seller")) {
                Intent intent3 = new Intent(getApplicationContext(), UploadActivity.class);
                startActivity(intent3);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("업로더 기능을 사용하실수 없습니다.");
                builder.setMessage("Seller만이 업로더 기능이 가능합니다.");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        } else if (id == R.id.nav_mypage){
            Intent intent4 = new Intent(getApplicationContext(), MypageActivity.class);
            startActivity(intent4);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}