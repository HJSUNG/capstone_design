package csecau.capstone.homeseek;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class BoardEdit extends AppCompatActivity {
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

        Intent intent = getIntent();

        titleRoom.setText(intent.getStringExtra("title"));
        detailExplain.setText(intent.getStringExtra("content"));
        String id_string = intent.getStringExtra("board");
        COUNT_ID = Integer.parseInt(id_string);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sTitle = titleRoom.getText().toString();
                String sDetail_info = detailExplain.getText().toString();

                boardManager db_manage = new boardManager();
                db_manage.execute("http://dozonexx.dothome.co.kr/boardEdit.php", sTitle, sDetail_info);

                titleRoom.setText("");
                detailExplain.setText("");
                finish();
            }

        });
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

            String data = "board="+COUNT_ID+"&title="+title+"&detail="+detail_inform;

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

    @Override
    public void onBackPressed(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("내용 수정 취소");

        alertDialogBuilder
                .setMessage("수정을 그만하시겠습니까?\n(종료를 누를 경우 바뀐 내용은 저장되지 않습니다)")
                .setCancelable(false)
                .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BoardEdit.this.finish();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
