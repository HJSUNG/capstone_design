package csecau.capstone.homeseek;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class BoardShow extends Activity {
    private BoardTitle pictureListAdapter;
    private ListView listView;
    private ArrayList<board_list> list_itemArrayList;
    private String JSONstring;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_list);

        listView = (ListView) findViewById(R.id.my_list);

        list_itemArrayList = new ArrayList<>();
        pictureListAdapter = new BoardTitle(this, list_itemArrayList);
        listView.setAdapter(pictureListAdapter);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingBtn);
        phpDown task = new phpDown();
        task.execute("http://dozonexx.dothome.co.kr/getBoard.php");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), BoardContentView.class);
                intent.putExtra("board", list_itemArrayList.get(position).getBoard());
                intent.putExtra("id", list_itemArrayList.get(position).getId());
                intent.putExtra("title", list_itemArrayList.get(position).getTitle());
                intent.putExtra("detail", list_itemArrayList.get(position).getDetail());
                startActivity(intent);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                startActivity(intent);
            }
        });
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

    private void showResult(){
        try{
            JSONObject jsonObject = new JSONObject(JSONstring);
            JSONArray jsonArray = jsonObject.getJSONArray("result");

            for(int i = 0; i<jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String board = item.getString("board");
                String id = item.getString("id");
                String title = item.getString("title");
                String detail = item.getString("detail");

                list_itemArrayList.add(new board_list(board, id, title, detail));
                pictureListAdapter.notifyDataSetChanged();

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}



