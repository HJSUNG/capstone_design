package csecau.capstone.homeseek;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static csecau.capstone.homeseek.MainActivity.user;

public class FavoriteActivity extends Activity {

    private PostingTitle pictureListAdapter;
    private ListView listView;
    private ArrayList<posting_list> list_itemArrayList;
    private String JSONstring;
    private String TAG = "bookmark";
    private static String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_posting_test);

        listView = (ListView) findViewById(R.id.my_list);

        list_itemArrayList = new ArrayList<>();
        pictureListAdapter = new PostingTitle(this, list_itemArrayList);
        listView.setAdapter(pictureListAdapter);
        Bookmark bookmark = new Bookmark();
        bookmark.execute("http://tjdghwns.cafe24.com/bookmark.php", user.info_ID);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), PostingActivity.class);
                intent.putExtra("title", list_itemArrayList.get(position).getTitle());
                intent.putExtra("homeid", list_itemArrayList.get(position).getHomeid());
                intent.putExtra("address", list_itemArrayList.get(position).getAddress());
                intent.putExtra("detailAddress", list_itemArrayList.get(position).getDetailaddress());
                intent.putExtra("explain", list_itemArrayList.get(position).getExplain());
                intent.putExtra("deposit", list_itemArrayList.get(position).getDeposit());
                intent.putExtra("monthly", list_itemArrayList.get(position).getMonthly());
                intent.putExtra("term", list_itemArrayList.get(position).getTerm());

                intent.putExtra("washing", list_itemArrayList.get(position).getWashing());
                intent.putExtra("refrigerator", list_itemArrayList.get(position).getRefrigerator());
                intent.putExtra("desk", list_itemArrayList.get(position).getDesk());
                intent.putExtra("bed", list_itemArrayList.get(position).getBed());
                intent.putExtra("microwave", list_itemArrayList.get(position).getMicrowave());
                intent.putExtra("closet", list_itemArrayList.get(position).getCloset());

                intent.putExtra("imageone", list_itemArrayList.get(position).getImageone());
                intent.putExtra("imagetwo", list_itemArrayList.get(position).getImagetwo());
                intent.putExtra("imagethree", list_itemArrayList.get(position).getImagethree());

                intent.putExtra("phonenum", list_itemArrayList.get(position).getPhoneNum());
                startActivity(intent);
            }
        });
    }

    class Bookmark extends AsyncTask<String, Void, String>{
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
            test = result;
            Log.d(TAG, test);
            String result_string_temp[] = (String[]) result.split(",");
            String result_string[] = new String[100];
            result_string[0]="No bookmark !";

            for(int i=0;i<result_string_temp.length-1;i++) {
                result_string[i] = result_string_temp[i+1];
            }

            if(result_string[0].equals("No bookmark !")) {
                Toast.makeText(FavoriteActivity.this, "No bookmark", Toast.LENGTH_SHORT).show();
            }

            phpDown task = new phpDown();
            task.execute("http://dozonexx.dothome.co.kr/getInform.php");
        }

        @Override
        protected String doInBackground(String...params) {
            String ID = (String)params[1];

            Log.d("lemona",ID);
            String serverURL = (String)params[0];
            String postParameters = "ID=" + ID;

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
                Log.d(TAG, "POST response code - " + responseStatusCode);

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
                Log.d(TAG, "Login Error ", e);
                return new String("ERROR: " + e.getMessage());
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

                String title = item.getString("title");
                String homeid = item.getString("homeid");
                String estateid = item.getString("estateid");
                String address = item.getString("address");
                String detailaddress = item.getString("detailaddress");
                String explain = item.getString("detail_exp");
                String deposit = item.getString("deposit");
                String monthly = item.getString("monthly");
                String term = item.getString("term");

                String washing = item.getString("washing");
                String refrigerator = item.getString("refrigerator");
                String desk = item.getString("desk");
                String bed = item.getString("bed");
                String microwave = item.getString("microwave");
                String closet = item.getString("closet");

                String imageone = item.getString("imageone");
                String imagetwo = item.getString("imagetwo");
                String imagethree = item.getString("imagethree");

                String phoneNum = item.getString("phonenum");
                Log.d("outaqua", homeid);
                if(test.indexOf(homeid)!=-1) {
                    Log.d("aqua", homeid);
                    list_itemArrayList.add(new posting_list(imageone, title, homeid, estateid, address, detailaddress, explain, deposit, monthly, term,
                            washing, refrigerator, desk, bed, microwave, closet, imageone, imagetwo, imagethree, phoneNum));
                    pictureListAdapter.notifyDataSetChanged();
                }

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
