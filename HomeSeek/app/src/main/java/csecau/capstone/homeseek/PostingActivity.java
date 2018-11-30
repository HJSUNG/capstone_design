package csecau.capstone.homeseek;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static csecau.capstone.homeseek.MainActivity.user;

public class PostingActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{
    TextView titleView;

    private GoogleMap mMap;

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    TextView contentView;
    TextView depositView;
    TextView monthlyView;
    TextView termView;
    TextView addressView, detailView, checkView;
    String washingChk, refrigeChk, deskChk, bedChk, microChk, closetChk;
    ImageView imageone, imagetwo, imagethree;
    ViewPager pager;
    Bitmap bm_one, bm_two, bm_three;
    Button beforeButton, nextButton;

//    String tv;
    double lat, lon;

    static int imagePosition = 0;
    static String homeID;
    static String imageONE, imageTWO, imageTHREE;
    final FirebaseStorage storage = FirebaseStorage.getInstance();
    static String imageoneURL, imagetwoURL, imagethreeURL;
    StorageReference storageReference = storage.getReferenceFromUrl("gs://estate-777.appspot.com").child("images");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_posting);
        mapFragment.getMapAsync(this);

        pager = (ViewPager)findViewById(R.id.Viewpager);
        titleView = (TextView)findViewById(R.id.titleWrite);
        contentView = (TextView)findViewById(R.id.contents);
        addressView = (TextView)findViewById(R.id.addressview);
        detailView = (TextView)findViewById(R.id.detailaddressview);
        imageone = (ImageView)findViewById(R.id.image1);
        imagetwo = (ImageView)findViewById(R.id.image2);
        imagethree = (ImageView)findViewById(R.id.image3);
        depositView = (TextView)findViewById(R.id.depositview);
        monthlyView = (TextView)findViewById(R.id.monthlyview);
        termView = (TextView)findViewById(R.id.termview);
        checkView = (TextView)findViewById(R.id.checkview);
        beforeButton = (Button)findViewById(R.id.before);
        nextButton = (Button)findViewById(R.id.next);

        Intent intent = getIntent();
        homeID = intent.getStringExtra("homeid");
        imageoneURL = intent.getStringExtra("imageone");
        imagetwoURL = intent.getStringExtra("imagetwo");
        imagethreeURL = intent.getStringExtra("imagethree");
        final String phoneNum = intent.getStringExtra("phonenum");
        ArrayList<String> data = new ArrayList<>();

        CustomAdapter adapter = new CustomAdapter(this, data);
        pager.setAdapter(adapter);

        storage.getReferenceFromUrl(imageoneURL).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                imageONE = uri.toString();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("glide", "실패");
            }
        });

        storage.getReferenceFromUrl(imagetwoURL).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                imageTWO = uri.toString();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("glide", "실패");
            }
        });

        storage.getReferenceFromUrl(imagethreeURL).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                imageTHREE= uri.toString();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("glide", "실패");
            }
        });

        Glide.with(getApplicationContext()).load(imageoneURL).into(imageone);


        setIMAGE(imageoneURL, 1);
        setIMAGE(imagetwoURL, 2);
        setIMAGE(imagethreeURL, 3);


        beforeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imagePosition == 1){
                    Glide.with(getApplicationContext()).load(imageONE).into(imageone);
                    imagePosition--;
                }
                else if(imagePosition == 2){
                    Glide.with(getApplicationContext()).load(imageTWO).into(imageone);
                    imagePosition--;
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imagePosition == 0){
                    Glide.with(getApplicationContext()).load(imageTWO).into(imageone);
                    imagePosition++;
                }
                else if(imagePosition == 1){
                    Glide.with(getApplicationContext()).load(imageTHREE).into(imageone);
                    imagePosition++;
                }
            }
        });

//        mapbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                List<Address> list = null;
//
//                String str = addressView.getText().toString();
//                {
//                    try {
//                        list = geocoder.getFromLocationName(str, 10);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        Log.e("test","입출력 오류 - 서버에서 주소변환 에러발생");
//                    }
//
//                    if(list != null){
//                        if(list.size() ==0){
//                            Log.e("test", "해당되는 주소 정보가 없습니다.");
//                        } else{
//                            tv=list.get(0).toString();
//                            Address addr = list.get(0);
//                            lat = addr.getLatitude();
//                            lon = addr.getLongitude();
//                        }
//                    }
//                }
//            }
//        });

        BottomNavigationView navigationView =(BottomNavigationView)findViewById(R.id.main_bar);

        BottomNavigationView.OnNavigationItemSelectedListener navigationListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.call:
                        Toast.makeText(getApplicationContext(), "Call", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:0"+phoneNum)));
                        return true;
                    case R.id.favorite:
//                        Toast.makeText(getApplicationContext(), "Favorites", Toast.LENGTH_LONG).show();

                        Insert_bookmark task = new Insert_bookmark();
                        task.execute("http://"+ MainActivity.IP_ADDRESS+"/insert_bookmark.php", user.info_ID,homeID);
                        return true;
                }
                return false;
            }
        };
        navigationView.setOnNavigationItemSelectedListener(navigationListener);

        titleView.setText(intent.getStringExtra("title"));
        addressView.setText(intent.getStringExtra("address"));
        detailView.setText(intent.getStringExtra("detailAddress"));
        contentView.setText(intent.getStringExtra("explain"));
        depositView.setText(intent.getStringExtra("deposit"));
        monthlyView.setText(intent.getStringExtra("monthly"));
        termView.setText(intent.getStringExtra("term"));

        washingChk = intent.getStringExtra("washing");
        refrigeChk = intent.getStringExtra("refrigerator");
        deskChk = intent.getStringExtra("desk");
        bedChk = intent.getStringExtra("bed");
        microChk = intent.getStringExtra("microwave");
        closetChk = intent.getStringExtra("closet");
        setCHECK(washingChk, refrigeChk, deskChk, bedChk, microChk, closetChk);
    }

    private Bitmap resize(Bitmap bm){

        Configuration config=getResources().getConfiguration();
        if(config.smallestScreenWidthDp>=600)
            bm = Bitmap.createScaledBitmap(bm, 300, 180, true);
        else if(config.smallestScreenWidthDp>=400)
            bm = Bitmap.createScaledBitmap(bm, 200, 120, true);
        else if(config.smallestScreenWidthDp>=360)
            bm = Bitmap.createScaledBitmap(bm, 180, 108, true);
        else
            bm = Bitmap.createScaledBitmap(bm, 160, 96, true);

        return bm;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_posting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_edit:
                Intent intent = new Intent(PostingActivity.this, EditActivity.class);
                intent.putExtra("title", titleView.getText());
                intent.putExtra("homeid",homeID);
                intent.putExtra("address", addressView.getText());
                intent.putExtra("detailaddress",detailView.getText());
                intent.putExtra("content", contentView.getText());
                intent.putExtra("deposit", String.valueOf(depositView.getText()));
                intent.putExtra("monthly", String.valueOf(monthlyView.getText()));
                intent.putExtra("term", String.valueOf(termView.getText()));

                intent.putExtra("washing", washingChk);
                intent.putExtra("refrigerator", refrigeChk);
                intent.putExtra("desk", deskChk);
                intent.putExtra("bed", bedChk);
                intent.putExtra("microwave", microChk);
                intent.putExtra("closet", closetChk);

                intent.putExtra("imageone",bm_one);
                intent.putExtra("imageoneURL", imageoneURL);
                intent.putExtra("imagetwo",bm_two);
                intent.putExtra("imagetwoURL", imagetwoURL);
                intent.putExtra("imagethree",bm_three);
                intent.putExtra("imagethreeURL", imagethreeURL);
                startActivity(intent);
                return true;
            case R.id.action_delete:
                Toast.makeText(this, "delete", Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setIMAGE(String imageURL, final int i){
        storage.getReferenceFromUrl(imageURL).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imageURL = uri.toString();
                switch(i){
                    case 1:
                        Glide.with(getApplicationContext()).load(imageURL).into(imageone);
                        Glide.with(getApplicationContext()).asBitmap().load(imageURL)
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                        bm_one = resource;
                                        bm_one = resize(bm_one);
                                    }
                                });
                        break;
                    case 2:
                        Glide.with(getApplicationContext()).load(imageURL).into(imagetwo);
                        Glide.with(getApplicationContext()).asBitmap().load(imageURL)
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                        bm_two = resource;
                                        bm_two = resize(bm_two);
                                    }
                                });
                        break;
                    case 3:
                        Glide.with(getApplicationContext()).load(imageURL).into(imagethree);
                        Glide.with(getApplicationContext()).asBitmap().load(imageURL)
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                        bm_three = resource;
                                        bm_three = resize(bm_three);
                                    }
                                });
                        break;
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("glide", "실패");
            }
        });
    }

    public void setCHECK(String washingChk, String refrigeChk, String deskChk, String bedChk, String microChk, String closetChk){
        String check = "";
        if(washingChk.equals("1")){
            check += "세탁기  ";
        }
        if(refrigeChk.equals("1")){
            check += "냉장고  ";
        }
        if(deskChk.equals("1")){
            check += "책상";
        }
        check+="\n";
        if(bedChk.equals("1")){
            check += "침대  ";
        }
        if(microChk.equals("1")){
            check += "전자레인지  ";
        }
        if(closetChk.equals("1")){
            check += "옷장";
        }
        checkView.setText(check);
    }


    private void mapLocationZoomInit(){
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapLocationZoomInit();
        final Geocoder geocoder = new Geocoder(this);

        List<Address> list = null;

        String str = addressView.getText().toString();

        try {
            list = geocoder.getFromLocationName(str, 10);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("test","입출력 오류 - 서버에서 주소변환시 에러발생");
        }

        if(list != null){
            if(list.size() ==0){
                Log.e("test", "해당되는 주소 정보가 없습니다.");
            } else{
//                tv=list.get(0).toString();
                Address addr = list.get(0);
                lat = addr.getLatitude();
                lon = addr.getLongitude();
            }
        }


        LatLng location = new LatLng(lat,lon);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,16));
    }


    class Insert_bookmark extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog2;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog2 = progressDialog2.show(LoginActivity.this, "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//            progressDialog2.dismiss();
            Toast.makeText(PostingActivity.this, result, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String...params) {
            String ID = (String)params[1];
            String item_num = (String)params[2];

            String serverURL = (String)params[0];
            String postParameters ="ID=" + ID + "&item_num=" + item_num;

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
                Log.d("@@@@", "POST response code - " + responseStatusCode);

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
                Log.d("@@@@", "Login Error ", e);
                return new String("ERROR: " + e.getMessage());
            }
        }
    }
}