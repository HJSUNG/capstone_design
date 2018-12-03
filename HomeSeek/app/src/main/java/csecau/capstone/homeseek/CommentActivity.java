package csecau.capstone.homeseek;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import static csecau.capstone.homeseek.MainActivity.user;

public class CommentActivity extends AppCompatActivity {
    private comment comment;
    private ListView list;
    private SwipeRefreshLayout refreshLayout = null;
    private ArrayList<comment_list> list_itemArrayList;
    private String JSONstring;
    private EditText commentText;
    private ImageButton imageButton;
    static String board;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        list = (ListView) findViewById(R.id.comment_list);
        commentText = (EditText)findViewById(R.id.comment_enter);
        imageButton = (ImageButton)findViewById(R.id.commentBtn);

        list_itemArrayList = new ArrayList<>();
        comment = new comment(this, list_itemArrayList);
        list.setAdapter(comment);
        Intent intent = getIntent();
        board = intent.getStringExtra("board");

        phpDown task = new phpDown();
        task.execute("http://dozonexx.dothome.co.kr/getComment.php");

        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list_itemArrayList.clear();
                phpDown task = new phpDown();
                task.execute("http://dozonexx.dothome.co.kr/getComment.php");
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.info_ID.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(CommentActivity.this);
                    builder.setMessage("로그인 한 사용자만 댓글 입력이 가능합니다.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }
                else {
                    String comment_text = commentText.getText().toString();
                    if (comment_text.length() != 0) {
                        uploadComment uploadComment = new uploadComment();
                        uploadComment.execute("http://dozonexx.dothome.co.kr/comment.php", comment_text);
                        list_itemArrayList.clear();
                        phpDown task = new phpDown();
                        task.execute("http://dozonexx.dothome.co.kr/getComment.php");
                    }
                    commentText.setText("");
                }
            }
        });
    }

    class uploadComment extends AsyncTask<String, Integer, String>{
        ProgressDialog loading;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            loading = ProgressDialog.show(CommentActivity.this, "Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            loading.dismiss();
        }

        @Override
        protected String doInBackground(String... params){
            String link = (String)params[0];
            String content = (String)params[1];

            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat time = new SimpleDateFormat("hhmmssSSS");
            String getDate = sdf.format(date);
            String getTime = time.format(date);
            int random = (int)(Math.random()*99)+1;
            String data = "board="+board+"&commentid="+getDate+getTime+String.valueOf(random)+"&id="+user.info_ID+"&comment="+content+"&datentime="+getDate+getTime;

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


    private class phpDown extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String str){
            super.onPostExecute(str);
            JSONstring = str;
            showResult();
            refreshLayout.setRefreshing(false);
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];

            String data = "board="+board;

            StringBuilder jsonHtml = new StringBuilder();
            try {
                URL url = new URL(serverURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();

                OutputStream writer = conn.getOutputStream();
                writer.write(data.getBytes("UTF-8"));
                writer.flush();
                writer.close();

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

    private void showResult(){
        try{
            JSONObject jsonObject = new JSONObject(JSONstring);
            JSONArray jsonArray = jsonObject.getJSONArray("result");

            for(int i = 0; i<jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String board = item.getString("board");
                String id = item.getString("id");
                String comment_in = item.getString("comment");
                String datetime = item.getString("datentime");
                list_itemArrayList.add(new comment_list(board, id, comment_in, datetime));

            }
            Comparator<comment_list> array = new Comparator<comment_list>() {
                @Override
                public int compare(comment_list o1, comment_list o2) {
                    int ret;

                    if(Long.parseLong(o1.getDatetime())< Long.parseLong(o2.getDatetime())){ ret = -1;}
                    else if(Long.parseLong(o1.getDatetime())==Long.parseLong(o2.getDatetime())){
                        ret = 0;
                    }
                    else ret = 1;
                    return ret;
                }
            };
            Collections.sort(list_itemArrayList, array);
            comment.notifyDataSetChanged();
        }
        catch(Exception e){
            e.printStackTrace();
        }


    }
}
