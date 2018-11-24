package csecau.capstone.homeseek;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

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
    ImageView imageone, imagetwo, imagethree;
    ViewPager pager;
    Button beforeButton, nextButton;
    static int imagePosition = 0;
    static String imageONE, imageTWO, imageTHREE;
    final FirebaseStorage storage = FirebaseStorage.getInstance();
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

        final String imageOne = intent.getStringExtra("imageone");
        final String imageTwo = intent.getStringExtra("imagetwo");
        final String imageThree = intent.getStringExtra("imagethree");
        final String phoneNum = intent.getStringExtra("phonenum");
        ArrayList<String> data = new ArrayList<>();

        CustomAdapter adapter = new CustomAdapter(this, data);
        pager.setAdapter(adapter);

        storage.getReferenceFromUrl(imageOne).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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

        storage.getReferenceFromUrl(imageTwo).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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

        storage.getReferenceFromUrl(imageThree).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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

        Glide.with(getApplicationContext()).load(imageOne).into(imageone);


        setIMAGE(imageOne, 1);
        setIMAGE(imageTwo, 2);
        setIMAGE(imageThree, 3);


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
                        Toast.makeText(getApplicationContext(), "Favorites", Toast.LENGTH_LONG).show();
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

        String washingChk = intent.getStringExtra("washing");
        String refrigeChk = intent.getStringExtra("refrigerator");
        String deskChk = intent.getStringExtra("desk");
        String bedChk = intent.getStringExtra("bed");
        String microChk = intent.getStringExtra("microwave");
        String closetChk = intent.getStringExtra("closet");
        setCHECK(washingChk, refrigeChk, deskChk, bedChk, microChk, closetChk);


    }

    public void setIMAGE(String imageURL, final int i){
        storage.getReferenceFromUrl(imageURL).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imageURL = uri.toString();
                switch(i){
                    case 1:
                        Glide.with(getApplicationContext()).load(imageURL).into(imageone);
                        break;
                    case 2:
                        Glide.with(getApplicationContext()).load(imageURL).into(imagetwo);
                        break;
                    case 3:
                        Glide.with(getApplicationContext()).load(imageURL).into(imagethree);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng SEOUL = new LatLng(37.5054544,126.9562383);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL)
                .title("중문");
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL,18));
    }
}