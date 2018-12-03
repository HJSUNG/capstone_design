package csecau.capstone.homeseek;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static csecau.capstone.homeseek.MainActivity.user;

public class MypageActivity extends AppCompatActivity{

    TextView nickname, ID, phone, type;
    TextView my_supervise, favorite_supervise,ID_supervise;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        nickname = (TextView) findViewById(R.id.mypage_nickname);
        ID = (TextView) findViewById(R.id.mypage_ID);
        phone = (TextView)findViewById(R.id.mypage_phone);
        type = (TextView)findViewById(R.id.mypage_type);
        my_supervise = (TextView)findViewById(R.id.my_supervise);
        favorite_supervise = (TextView)findViewById(R.id.favorite_supervise);
        ID_supervise = (TextView)findViewById(R.id.ID_supervise);

        nickname.setText(user.info_nickname);
        ID.setText(user.info_ID);
        phone.setText(user.info_phone);
        type.setText(user.info_type);

        my_supervise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.info_type.equals("Seller")){
                    Intent intent = new Intent(getApplicationContext(), ModifyActivity.class);
                    startActivity(intent);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MypageActivity.this);
//                    builder.setTitle("매물관리 기능을 사용하실수 없습니다.");
                    builder.setMessage("Seller만이 매물관리 기능이 가능합니다.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });
        favorite_supervise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FavoriteActivity.class);
                startActivity(intent);
            }
        });
        ID_supervise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MypageActivity.this);
                alert_confirm.setMessage("회원탈퇴를 진행하시겠습니까 ?").setCancelable(false).setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Delete delete_task = new Delete();
                                delete_task.execute("http://tjdghwns.cafe24.com/delete.php", user.info_ID);
                                DeleteEstate deleteEstate = new DeleteEstate();
                                deleteEstate.execute("http://dozonexx.dothome.co.kr/deleteEstate.php", user.info_ID);
                            }
                        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //No
                        return;
                    }
                });
                AlertDialog alert = alert_confirm.create();
                alert.show();
            }
        });

    }

    class Delete extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Toast.makeText(MypageActivity.this, result, Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String...params) {
            String ID = (String)params[1];

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
                Log.d("@@@", "POST response code - " + responseStatusCode);

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
                Log.d("@@@", "Login Error ", e);
                return new String("ERROR: " + e.getMessage());
            }
        }
    }

    class DeleteEstate extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Toast.makeText(MypageActivity.this, result, Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String...params) {
            String ID = (String)params[1];

            String serverURL = (String)params[0];
            String postParameters = "estateid=" + ID;

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
                Log.d("@@@", "POST response code - " + responseStatusCode);

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
                Log.d("@@@", "Login Error ", e);
                return new String("ERROR: " + e.getMessage());
            }
        }
    }

}
