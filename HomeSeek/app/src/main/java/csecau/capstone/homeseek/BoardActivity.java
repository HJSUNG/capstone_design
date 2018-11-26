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
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import csecau.capstone.homeseek.db.SimpleDB;
import csecau.capstone.homeseek.vo.ArticleVO;

public class BoardActivity extends AppCompatActivity {
    EditText titleRoom, detailExplain;
    Button registerBtn;
    static int COUNT_ID;
    private static String JSONString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

            titleRoom = (EditText)findViewById(R.id.title);
            detailExplain = (EditText)findViewById(R.id.detail_exp_textView);
            registerBtn = (Button)findViewById(R.id.RegisterBtn);

            IDManager IDManager = new IDManager();
            IDManager.execute("http://dozonexx.dothome.co.kr/boardID.php");


            registerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String sTitle = titleRoom.getText().toString();
                    String sDetail_info = detailExplain.getText().toString();

                    boardManager db_manage = new boardManager();
                    db_manage.execute("http://dozonexx.dothome.co.kr/boardUpload.php", sTitle, sDetail_info);

                    titleRoom.setText("");
                    detailExplain.setText("");
                }

            });
    }

    class IDManager extends AsyncTask<String, Void, String>{
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
                String count_id = jsonArray.getString("board");
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

    class boardManager extends AsyncTask<String, Integer, String>{

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

            String title = (String)params[1];
            String detail_inform = (String)params[2];

            String data = "board="+COUNT_ID+"&id="+"lll"+"&title="+title+"&detail="+detail_inform;

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
