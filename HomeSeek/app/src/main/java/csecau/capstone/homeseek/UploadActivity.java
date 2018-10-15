package csecau.capstone.homeseek;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class UploadActivity extends AppCompatActivity{
    EditText titleRoom, roomaddress, roomdetail, detailExplain;
    CheckBox chkWasing, chkRefri, chkCloset, chkDesk, chkBed, chkMicro;
    ImageView imageView1, imageView2, imageView3;
    Button registerBtn, imageBtn, findAddress;
    String sTitle, sAddress;
    phpDown task;
    private static final int IMAGE_REQUEST_CODE = 3;
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    PermissionCheck permissionCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleRoom = (EditText)findViewById(R.id.title);
        roomaddress = (EditText)findViewById(R.id.address);
        roomdetail = (EditText)findViewById(R.id.detailaddress);
        detailExplain = (EditText)findViewById(R.id.detail_exp);
        registerBtn = (Button)findViewById(R.id.btnRegister);
        imageBtn = (Button)findViewById(R.id.btnImage);
        findAddress = (Button)findViewById(R.id.find_address);
        chkWasing = (CheckBox)findViewById(R.id.washingChk);
        chkRefri = (CheckBox)findViewById(R.id.refriChk);
        chkCloset = (CheckBox)findViewById(R.id.closetChk);
        chkDesk = (CheckBox)findViewById(R.id.deskChk);
        chkBed = (CheckBox)findViewById(R.id.bedChk);
        chkMicro = (CheckBox)findViewById(R.id.microwaveChk);
        imageView1 = (ImageView)findViewById(R.id.image1);
        imageView2 = (ImageView)findViewById(R.id.image2);
        imageView3 = (ImageView)findViewById(R.id.image3);
        task = new phpDown();


        permissionCheck = new PermissionCheck();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sTitle = titleRoom.getText().toString();
                sAddress = roomaddress.getText().toString();
                dbManager db_manage = new dbManager();
                Toast.makeText(UploadActivity.this, "등록되었습니다", Toast.LENGTH_SHORT).show();
                task.execute("http://localhost/test.php");
                db_manage.execute();
                insert(v);
                insertChk(v);
            }
        });

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"complete"),IMAGE_REQUEST_CODE);
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

    public void insertChk(View view){
        boolean washingChk = chkWasing.isChecked();
        boolean refriChk = chkRefri.isChecked();
        boolean deskChk = chkDesk.isChecked();
        boolean bedChk = chkBed.isChecked();
        boolean microChk = chkMicro.isChecked();
        boolean closetChk = chkCloset.isChecked();

        insertToBoolean(washingChk, refriChk, deskChk, bedChk, microChk, closetChk);
    }

    private void insertToBoolean(boolean ck1, boolean ck2, boolean ck3, boolean ck4, boolean ck5, boolean ck6){
        class InsertDataChk extends AsyncTask<Boolean, Void, String>{
            ProgressDialog loading_chk;

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading_chk = ProgressDialog.show(UploadActivity.this, "Wait",null, true, true);
            }


            @Override
            protected String doInBackground(Boolean... params){
                try{
                    boolean washChk = (boolean)params[0];
                    boolean refrChk = (boolean)params[1];
                    boolean desk = (boolean)params[2];
                    boolean bed = (boolean)params[3];
                    boolean micro = (boolean)params[4];
                    boolean closet = (boolean)params[4];

                    String link = "http://10.0.2.2/check.php";
                    String data = URLEncoder.encode("washing","UTF-8")+"="+URLEncoder.encode(String.valueOf(washChk),"UTF-8");
                    data += "&"+URLEncoder.encode("refrigerator","UTF-8")+"="+URLEncoder.encode(String.valueOf(refrChk),"UTF-8");
                    data += "&"+URLEncoder.encode("desk","UTF-8")+"="+URLEncoder.encode(String.valueOf(desk),"UTF-8");
                    data += "&"+URLEncoder.encode("bed","UTF-8")+"="+URLEncoder.encode(String.valueOf(bed),"UTF-8");
                    data += "&"+URLEncoder.encode("microwave","UTF-8")+"="+URLEncoder.encode(String.valueOf(micro),"UTF-8");
                    data += "&"+URLEncoder.encode("closet","UTF-8")+"="+URLEncoder.encode(String.valueOf(closet),"UTF-8");

                    URL url = new URL(link);
                    URLConnection connection = url.openConnection();

                    connection.setDoOutput(true);
                    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

                    writer.write(data);
                    //writer.flush();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    StringBuilder stringBuilder = new StringBuilder();
                    String line = null;

                    while((line = bufferedReader.readLine())!= null){
                        stringBuilder.append(line);
                        break;
                    }
                    return stringBuilder.toString();
                } catch(Exception e){
                    return new String("Exception: "+e.getMessage());
                }

            }

        }
        InsertDataChk taskChk = new InsertDataChk();
        taskChk.execute(ck1, ck2, ck3, ck4, ck5, ck6);
    }

    public void insert(View view){
        String home_id = "1";
        String estate_id = "55";
        String address = roomaddress.getText().toString();
        String detail_addr = roomdetail.getText().toString();
        String detail_info = detailExplain.getText().toString();

        insertToDatabase(home_id, estate_id, address, detail_addr, detail_info);
    }

    private void insertToDatabase(String homeid, String estateid, String address, String detailaddress, String infoDetail){
        class InsertData extends AsyncTask<String, Void, String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(UploadActivity.this, "Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params){
                try{
                    String homeid = (String)params[0];
                    String estateid = (String)params[1];
                    String address = (String)params[2];
                    String detailaddress = (String)params[3];
                    String infoDetail = (String)params[4];

                    String link = "http://10.0.2.2/test.php";
                    String data = URLEncoder.encode("homeid","UTF-8")+"="+URLEncoder.encode(homeid,"UTF-8");
                    data += "&"+URLEncoder.encode("estateid","UTF-8")+"="+URLEncoder.encode(estateid,"UTF-8");
                    data += "&"+URLEncoder.encode("address","UTF-8")+"="+URLEncoder.encode(address,"UTF-8");
                    data += "&"+URLEncoder.encode("detailaddress","UTF-8")+"="+URLEncoder.encode(detailaddress,"UTF-8");
                    data += "&"+URLEncoder.encode("detail_exp","UTF-8")+"="+URLEncoder.encode(infoDetail,"UTF-8");

                    URL url = new URL(link);
                    URLConnection connection = url.openConnection();

                    connection.setDoOutput(true);
                    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

                    writer.write(data);
                    //writer.flush();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    StringBuilder stringBuilder = new StringBuilder();
                    String line = null;

                    while((line = bufferedReader.readLine())!= null){
                        stringBuilder.append(line);
                        break;
                    }
                    return stringBuilder.toString();
                } catch(Exception e){
                    return new String("Exception: "+e.getMessage());
                }

            }

        }
        InsertData task = new InsertData();
        task.execute(homeid,estateid, address, detailaddress,infoDetail);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        switch(requestCode){
            case SEARCH_ADDRESS_ACTIVITY:
                if(resultCode == RESULT_OK){
                    String data = intent.getExtras().getString("data");
                    if (data != null)
                        roomaddress.setText(data);
                }break;
        }
    }
    /*private void getAlbum(){
        boolean isAlbum = permissionCheck.isCheck(MainActivity.this, new ViewpagerActivity() , "hello","Album");
        if(isAlbum){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                try{
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    startActivityForResult(Intent.createChooser(intent,"다중"), REQUEST_TAKE_ALBUM);
                }
                catch(Exception e){
                    Log.e("error",e.toString());
                }
            }
            else{
                Log.e("kitkat under","..");
            }
        }
    }*/

    public class dbManager extends AsyncTask<Void, Integer, Void>{
        @Override
        protected Void doInBackground(Void...unused){
            String parameter = "title="+titleRoom+"&address="+roomaddress+"";
            try{
                URL url = new URL("localhost/test.php");
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.connect();

                OutputStream outs = connection.getOutputStream();
                outs.write(parameter.getBytes("UTF-8"));
                outs.flush();
                outs.close();

                InputStream inputStream= null;
                BufferedReader bufferedReader = null;
                String data = "";

                inputStream = connection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 8*1024);
                String line = null;
                StringBuffer buffer = new StringBuffer();

                while((line = bufferedReader.readLine())!= null){
                    buffer.append(line+"\n");
                }
                data = buffer.toString().trim();
                Log.e("RECV DATA", data);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class phpDown extends AsyncTask<String, Integer, String>{
        @Override
        protected String doInBackground(String... urls){
            StringBuilder jsonHtml = new StringBuilder();
            try{
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                if(connection!= null){
                    connection.setConnectTimeout(10000);
                    connection.setUseCaches(false);
                    if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
                        for(;;){
                            String line = bufferedReader.readLine();
                            if(line==null) break;

                            jsonHtml.append(line+"\n");
                        }
                        bufferedReader.close();
                    }
                    connection.disconnect();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return jsonHtml.toString();
        }
    }
}