package csecau.capstone.homeseek;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static csecau.capstone.homeseek.MainActivity.user;

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

                if(BoardShow.activity!=null) {
                    BoardShow.activity.finish();
                }

                startActivity(new Intent(BoardActivity.this, BoardShow.class));
                finish();
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

            String data = "board="+COUNT_ID+"&id="+user.info_ID+"&title="+title+"&detail="+detail_inform;

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
