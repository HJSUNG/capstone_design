package csecau.capstone.homeseek;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class testposting extends Activity {
    private PostingTitle pictureListAdapter;
    private ListView listView;
    private ArrayList<posting_list> list_itemArrayList;
    private String JSONstring;
    private Button basic, money, areaDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting_test);

        listView = (ListView) findViewById(R.id.my_list);

        basic = (Button)findViewById(R.id.basic);
        money = (Button)findViewById(R.id.money);
        areaDesc = (Button)findViewById(R.id.areaDesc);

        list_itemArrayList = new ArrayList<>();
        pictureListAdapter = new PostingTitle(this, list_itemArrayList);
        listView.setAdapter(pictureListAdapter);
        phpDown task = new phpDown();
        task.execute("http://dozonexx.dothome.co.kr/getInform.php");

        basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comparator<posting_list> basic_array = new Comparator<posting_list>() {
                    @Override
                    public int compare(posting_list o1, posting_list o2) {
                        int ret;

                        if(Integer.parseInt(o1.getHomeid())< Integer.parseInt(o2.getHomeid())){ ret = -1;}
                        else if(Integer.parseInt(o1.getHomeid())==Integer.parseInt(o2.getHomeid())){
                            ret = 0;
                        }
                        else ret = 1;
                        return ret;
                    }
                };
                Collections.sort(list_itemArrayList, basic_array);
                pictureListAdapter.notifyDataSetChanged();
            }
        });

        money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comparator<posting_list> monthly_array = new Comparator<posting_list>() {
                    @Override
                    public int compare(posting_list o1, posting_list o2) {
                        int ret;

                        if(Integer.parseInt(o1.getMonthly())< Integer.parseInt(o2.getMonthly())){ ret = -1;}
                        else if(Integer.parseInt(o1.getMonthly())== Integer.parseInt(o2.getMonthly())){
                            ret = 0;
                        }
                        else ret = 1;
                        return ret;
                    }
                };
                Collections.sort(list_itemArrayList, monthly_array);
                pictureListAdapter.notifyDataSetChanged();
            }
        });

        areaDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comparator<posting_list> area_array = new Comparator<posting_list>() {
                    @Override
                    public int compare(posting_list o1, posting_list o2) {
                        int ret;

                        if(Integer.parseInt(o1.getArea())< Integer.parseInt(o2.getArea())){ ret = -1;}
                        else if(Integer.parseInt(o1.getArea())==Integer.parseInt(o2.getArea())){
                            ret = 0;
                        }
                        else ret = 1;
                        return ret;
                    }
                };
                Collections.sort(list_itemArrayList, area_array);
                pictureListAdapter.notifyDataSetChanged();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), PostingActivity.class);
                intent.putExtra("title", list_itemArrayList.get(position).getTitle());
                intent.putExtra("homeid", list_itemArrayList.get(position).getHomeid());
                intent.putExtra("estateid", list_itemArrayList.get(position).getEstateid());
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
                intent.putExtra("manage", list_itemArrayList.get(position).getManage());
                intent.putExtra("area", list_itemArrayList.get(position).getArea());
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
                String Manage = item.getString("manage");
                String Area = item.getString("area");

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
                    list_itemArrayList.add(new posting_list(imageone, title, homeid, estateid, address, detailaddress, explain, deposit, monthly, term,
                            washing, refrigerator, desk, bed, microwave, closet, imageone, imagetwo, imagethree, phoneNum, Manage, Area));
                    pictureListAdapter.notifyDataSetChanged();
                }

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
