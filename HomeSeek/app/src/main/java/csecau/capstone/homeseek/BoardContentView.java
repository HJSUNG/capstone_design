package csecau.capstone.homeseek;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BoardContentView extends AppCompatActivity {
    TextView titleView, idView, contentView, commentView;
    String board, title, id, content;
    ImageButton send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_show);
        titleView = (TextView)findViewById(R.id.titleview);
        idView = (TextView)findViewById(R.id.idview);
        contentView = (TextView)findViewById(R.id.contentsview);
        commentView = (TextView)findViewById(R.id.commenttext);
        send = (ImageButton)findViewById(R.id.sendBtn);

        Intent intent = getIntent();
        board = intent.getStringExtra("board");
        id = intent.getStringExtra("id");
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("detail");

        titleView.setText(title);
        idView.setText(id);
        contentView.setText(content);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentView.setText("");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_edit:
                Intent intent = new Intent(BoardContentView.this, BoardEdit.class);
                intent.putExtra("title", titleView.getText());
                intent.putExtra("content", contentView.getText());
                intent.putExtra("board", board);
                startActivity(intent);
                return true;
            case R.id.action_delete:
                deleteManage deleteManage = new deleteManage();
                deleteManage.execute("http://dozonexx.dothome.co.kr/deleteBoard.php", board);
                startActivity(new Intent(BoardContentView.this, navigation_main.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class deleteManage extends AsyncTask<String, Integer, String> {

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
            String homeid = (String)params[1];

            String data = "board="+homeid;

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