package csecau.capstone.homeseek;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MapsEstateList extends AppCompatActivity {
    private String JSONstring;
    private ArrayList<posting_list> list_itemArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        list_itemArrayList = new ArrayList<>();

        phpDown task = new phpDown();
        task.execute("http://dozonexx.dothome.co.kr/getInform.php");



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

            for(int i = 0; i<jsonArray.length(); i++){
                JSONObject item = jsonArray.getJSONObject(i);

                String title = item.getString("title");
                String homeid = item.getString("homeid");
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
                Intent intent = getIntent();
                int[] checkARRAY = intent.getIntArrayExtra("selected");
                boolean addList = true;

                if (checkARRAY[0] == 1 && washing.equals("0")) {
                    addList = false;
                }
                if (checkARRAY[1] == 1 && refrigerator.equals("0")) {
                    addList = false;
                }
                if (checkARRAY[2] == 1 && desk.equals("0")) {
                    addList = false;
                }
                if (checkARRAY[3] == 1 && bed.equals("0")) {
                    addList = false;
                }
                if (checkARRAY[4] == 1 && microwave.equals("0")) {
                    addList = false;
                }
                if (checkARRAY[5] == 1 && closet.equals("0")) {
                    addList = false;
                }
                if (addList) {
                    list_itemArrayList.add(new posting_list(R.mipmap.ic_launcher, title, homeid, address, detailaddress, explain, deposit, monthly, term,
                            washing, refrigerator, desk, bed, microwave, closet, imageone, imagetwo, imagethree, phoneNum));
                }
            }
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            intent.putExtra("marker_list", list_itemArrayList);
            startActivity(intent);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
