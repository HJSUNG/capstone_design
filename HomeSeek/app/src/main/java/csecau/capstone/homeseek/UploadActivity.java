package csecau.capstone.homeseek;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadActivity extends AppCompatActivity{
    private static int COUNT_ID;
    private static int COUNT_ESTATE = 100;
    private static String JSONString;

    private Uri filePath, filePath2, filePath3;

    private static final int IMAGE_REQUEST_CODE_ONE = 1;
    private static final int IMAGE_REQUEST_CODE_TWO = 2;
    private static final int IMAGE_REQUEST_CODE_THREE = 3;
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    private static String image_one = null;
    private static String image_two = null;
    private static String image_three = null;

    EditText titleRoom, roomaddress, roomdetail, detailExplain, deposit, monthly, term;
    CheckBox chkWasing, chkRefri, chkDesk, chkBed, chkMicro, chkCloset;
    ImageView imageView1, imageView2, imageView3;
    Button registerBtn, findAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        titleRoom = (EditText)findViewById(R.id.title);
        roomaddress = (EditText)findViewById(R.id.address);
        roomdetail = (EditText)findViewById(R.id.detailaddress);
        detailExplain = (EditText)findViewById(R.id.detail_exp);
        deposit = (EditText)findViewById(R.id.depositwrite);
        monthly = (EditText)findViewById(R.id.monthlywrite);
        term = (EditText)findViewById(R.id.termwrite);


        registerBtn = (Button)findViewById(R.id.btnRegister);
        findAddress = (Button)findViewById(R.id.find_address);

        chkWasing = (CheckBox)findViewById(R.id.washingChk);
        chkRefri = (CheckBox)findViewById(R.id.refriChk);
        chkDesk = (CheckBox)findViewById(R.id.deskChk);
        chkBed = (CheckBox)findViewById(R.id.bedChk);
        chkMicro = (CheckBox)findViewById(R.id.microwaveChk);
        chkCloset = (CheckBox)findViewById(R.id.closetChk);

        imageView1 = (ImageView)findViewById(R.id.image1);
        imageView2 = (ImageView)findViewById(R.id.image2);
        imageView3 = (ImageView)findViewById(R.id.image3);
        imageView1.setImageResource(R.drawable.plusicon);
        imageView2.setImageResource(R.drawable.plusicon);
        imageView3.setImageResource(R.drawable.plusicon);
        roomaddress.setEnabled(false);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sTitle = titleRoom.getText().toString();
                String sAddress = roomaddress.getText().toString();
                String sDetailAddr = roomdetail.getText().toString();
                String sDetail_info = detailExplain.getText().toString();
                String sDeposit = deposit.getText().toString();
                String sMonthly = monthly.getText().toString();
                String sTerm = term.getText().toString();
                String[] checkPoint = new String[6];
                if(chkWasing.isChecked()){
                    checkPoint[0] = "1";
                } else{ checkPoint[0] = "0"; }
                if(chkRefri.isChecked()){
                    checkPoint[1] = "1";
                } else{ checkPoint[1] = "0";}
                if(chkDesk.isChecked()){
                    checkPoint[2] = "1";
                }else{ checkPoint[2] = "0";}
                if(chkBed.isChecked()){
                    checkPoint[3] = "1";
                }else{ checkPoint[3] = "0";}
                if(chkMicro.isChecked()){
                    checkPoint[4] = "1";
                }else{ checkPoint[4] = "0";}
                if(chkCloset.isChecked()){
                    checkPoint[5] = "1";
                }else{ checkPoint[5] = "0";}

                homeIDManager homeIDManager = new homeIDManager();
                homeIDManager.execute("http://dozonexx.dothome.co.kr/homeid.php");

                dbManager db_manage = new dbManager();
                db_manage.execute("http://dozonexx.dothome.co.kr/check.php", sTitle, sAddress, sDetailAddr, sDetail_info, sDeposit, sMonthly, sTerm);
                checkManager check_manage = new checkManager();
                check_manage.execute("http://dozonexx.dothome.co.kr/checkDB.php", checkPoint[0], checkPoint[1], checkPoint[2], checkPoint[3], checkPoint[4], checkPoint[5]);
                imageManager image_manage = new imageManager();
                uploadFile();
                uploadFile2();
                uploadFile3();
                image_manage.execute("http://dozonexx.dothome.co.kr/imageUpload.php", image_one, image_two, image_three);


                titleRoom.setText("");
                roomaddress.setText("");
                roomdetail.setText("");
                detailExplain.setText("");
                deposit.setText("");
                monthly.setText("");
                term.setText("");
                chkWasing.setChecked(false);
                chkRefri.setChecked(false);
                chkDesk.setChecked(false);
                chkBed.setChecked(false);
                chkMicro.setChecked(false);
                chkCloset.setChecked(false);

                COUNT_ESTATE++;
            }

        });

        imageView1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_REQUEST_CODE_ONE);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_REQUEST_CODE_TWO);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_REQUEST_CODE_THREE);
            }
        });

        findAddress.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(UploadActivity.this, daumAddress.class);
                startActivityForResult(intent, SEARCH_ADDRESS_ACTIVITY);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        switch(requestCode){
            case SEARCH_ADDRESS_ACTIVITY:
                if(resultCode == RESULT_OK){
                    String data = intent.getExtras().getString("data");
                    if (data != null)
                        roomaddress.setText(data);
                }
                break;
            case IMAGE_REQUEST_CODE_ONE:
                if(resultCode == RESULT_OK){
                    try {
                        filePath = intent.getData();
                        InputStream in = getContentResolver().openInputStream(intent.getData());
                        Bitmap image = BitmapFactory.decodeStream(in);
                        image = resize(image);
                        in.close();
                        imageView1.setImageBitmap(image);
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case IMAGE_REQUEST_CODE_TWO:
                if(resultCode == RESULT_OK){
                    try {
                        filePath2 = intent.getData();
                        InputStream in = getContentResolver().openInputStream(intent.getData());
                        Bitmap image = BitmapFactory.decodeStream(in);
                        image = resize(image);
                        in.close();
                        imageView2.setImageBitmap(image);
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case IMAGE_REQUEST_CODE_THREE:
                if(resultCode == RESULT_OK){
                    try {
                        filePath3 = intent.getData();
                        InputStream in = getContentResolver().openInputStream(intent.getData());
                        Bitmap image = BitmapFactory.decodeStream(in);
                        image = resize(image);
                        in.close();
                        imageView3.setImageBitmap(image);
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
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

    private void uploadFile(){
        if(filePath!= null){
            FirebaseStorage storage = FirebaseStorage.getInstance();

            SimpleDateFormat format = new SimpleDateFormat("yyyyMMHH_mmss");
            Date now = new Date();
            String filename = format.format(now)+COUNT_ID+"1.jpg";
            StorageReference storageRef = storage.getReferenceFromUrl("gs://estate-777.appspot.com").child("images/"+filename);
            image_one = "gs://estate-777.appspot.com/images/"+filename;
            storageRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //@SuppressWarnings("VisibleForTests")
                            //double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        }
                    });
        }
    }

    private void uploadFile2(){
        if(filePath2!= null){
            FirebaseStorage storage = FirebaseStorage.getInstance();

            SimpleDateFormat format = new SimpleDateFormat("yyyyMMHH_mmss");
            Date now = new Date();
            String filename = format.format(now)+COUNT_ID+"2.jpg";
            StorageReference storageRef = storage.getReferenceFromUrl("gs://estate-777.appspot.com").child("images/"+filename);
            image_two = "gs://estate-777.appspot.com/images/"+filename;
            storageRef.putFile(filePath2)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //@SuppressWarnings("VisibleForTests")
                            //double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        }
                    });
        }
    }

    private void uploadFile3(){
        if(filePath3!= null){
            FirebaseStorage storage = FirebaseStorage.getInstance();

            SimpleDateFormat format = new SimpleDateFormat("yyyyMMHH_mmss");
            Date now = new Date();
            String filename = format.format(now)+COUNT_ID+"3.jpg";
            StorageReference storageRef = storage.getReferenceFromUrl("gs://estate-777.appspot.com").child("images/"+filename);
            image_three = "gs://estate-777.appspot.com/images/"+filename;
            storageRef.putFile(filePath3)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //@SuppressWarnings("VisibleForTests")
                            //double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        }
                    });
        }
    }

    class homeIDManager extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String str){
            super.onPostExecute(str);
            JSONString = str;
            try{
                JSONObject jsonObject = new JSONObject(JSONString);
                JSONObject jsonArray = jsonObject.getJSONObject("result");
                String count_id = jsonArray.getString("homeid");
                COUNT_ID = Integer.parseInt(count_id);
                COUNT_ID++;
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];
            StringBuilder jsonHtml = new StringBuilder();
            try {
                URL url = new URL(serverURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();

                InputStream input;
                int responseCode = conn.getResponseCode();
                if(responseCode== HttpURLConnection.HTTP_OK){
                    input = conn.getInputStream();
                }
                else{
                    input = conn.getErrorStream();
                }
                InputStreamReader inputStreamReader = new InputStreamReader(input, "UTF-8");
                BufferedReader br = new BufferedReader(inputStreamReader);
                String line;
                while ((line = br.readLine()) != null) {
                    jsonHtml.append(line);
                }
                br.close();
                return jsonHtml.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    class imageManager extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... params){
            String link = (String)params[0];
            String imageone = (String)params[1];
            String imagetwo = (String)params[2];
            String imagethree = (String)params[3];

            String datas = "homeid="+COUNT_ID+"&imageone="+imageone+"&imagetwo="+imagetwo+"&imagethree="+imagethree;

            try{
                URL url = new URL(link);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(5000);
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("POST");
                connection.connect();

                OutputStream writer = connection.getOutputStream();
                writer.write(datas.getBytes("UTF-8"));
                writer.flush();
                writer.close();

                int responseStatusCode = connection.getResponseCode();
                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = connection.getInputStream();
                }
                else {
                    inputStream = connection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder stringBuilder = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine())!= null){
                    stringBuilder.append(line);
                }

                bufferedReader.close();

                return stringBuilder.toString();
            }
            catch(Exception e){
                return new String("Exception: "+e.getMessage());
            }
        }
    }

    class checkManager extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... params){
            String link = (String)params[0];
            String washing = (String)params[1];
            String refrigerator = (String)params[2];
            String desk = (String)params[3];
            String bed = (String)params[4];
            String microwave = (String)params[5];
            String closet = (String)params[6];

            String datas = "homeid="+COUNT_ID;
            if(washing == "1"){
                datas += "&washing="+washing;
            }
            if(refrigerator == "1"){
                datas += "&refrigerator="+refrigerator;
            }
            if(desk == "1"){
                datas += "&desk="+desk;
            }if(bed == "1"){
                datas += "&bed="+bed;
            }
            if(microwave== "1"){
                datas += "&microwave="+microwave;
            }
            if(closet == "1"){
                datas += "&closet="+closet;
            }

            try{
                URL url = new URL(link);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(5000);
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("POST");
                connection.connect();

                OutputStream writer = connection.getOutputStream();
                writer.write(datas.getBytes("UTF-8"));
                writer.flush();
                writer.close();

                int responseStatusCode = connection.getResponseCode();
                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = connection.getInputStream();
                }
                else {
                    inputStream = connection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder stringBuilder = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine())!= null){
                    stringBuilder.append(line);
                }

                bufferedReader.close();

                return stringBuilder.toString();
            }
            catch(Exception e){
                return new String("Exception: "+e.getMessage());
            }
        }
    }

    class dbManager extends AsyncTask<String, Integer, String>{
        ProgressDialog loading;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            loading = ProgressDialog.show(UploadActivity.this, "Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            loading.dismiss();
        }

        @Override
        protected String doInBackground(String... params){
            String link = (String)params[0];

            String title = (String)params[1];
            String address = (String)params[2];
            String detailAddress = (String)params[3];
            String detail_inform = (String)params[4];
            String deposit = (String)params[5];
            String monthly = (String)params[6];
            String term = (String)params[7];

            String data = "title="+title+"&homeid="+COUNT_ID+"&estateid="+COUNT_ESTATE+"&address="+address+"&detailaddress="+detailAddress+"&detail_exp="+detail_inform;
            data += "&deposit="+deposit+"&monthly="+monthly+"&term="+term+"&visible=1";

            try{
                URL url = new URL(link);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(5000);
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("POST");
                connection.connect();

                OutputStream writer = connection.getOutputStream();
                writer.write(data.getBytes("UTF-8"));
                writer.flush();
                writer.close();

                int responseStatusCode = connection.getResponseCode();
                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = connection.getInputStream();
                }
                else {
                    inputStream = connection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder stringBuilder = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine())!= null){
                    stringBuilder.append(line);
                }

                bufferedReader.close();

                return stringBuilder.toString();
            }
            catch(Exception e){
                return new String("Exception: "+e.getMessage());
            }
        }
    }
}
