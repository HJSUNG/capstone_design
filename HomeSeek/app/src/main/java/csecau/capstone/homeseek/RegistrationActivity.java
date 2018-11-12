package csecau.capstone.homeseek;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

public class RegistrationActivity extends AppCompatActivity {
//ip 주소 고정할수 있는 방법 없나?
    private static String TAG = "phptest";
    private static boolean IDcheck_done = false;

    private EditText IDEdittext;
    private EditText PWEdittext;
    private EditText nicknameEdittext;
    private EditText confirmPWEdittext;
    private EditText phoneEdittext;

    private Button checkButton;
    private Button doneButton;

    private RadioGroup user_typeRg;
    private RadioButton user_type_selected;

    private TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        IDEdittext = (EditText) findViewById(R.id.IDregister);
        PWEdittext = (EditText) findViewById(R.id.PWregister);
        confirmPWEdittext = (EditText)findViewById(R.id.ConfirmPW);
        nicknameEdittext = (EditText) findViewById(R.id.NicknameRegister);
        phoneEdittext = (EditText) findViewById(R.id.phoneregister);

        checkButton = (Button) findViewById(R.id.IDcheck);
        doneButton = (Button) findViewById(R.id.DoneRegister);

        user_typeRg = (RadioGroup) findViewById(R.id.user_type);
        user_type_selected = (RadioButton) findViewById(user_typeRg.getCheckedRadioButtonId());

        textResult = (TextView) findViewById(R.id.TextResultRegister);


        //중복 아이디 체크할 부분 (지금 primary key가 ID 로 되있어서 자체적으로 체크되는데 이걸 안드로이드로 깔끔하게 할수 있나)
        //어플 레벨에서 아예 체크해서 넘기는 걸로

        checkButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String IDcompare= IDEdittext.getText().toString();
                CheckID task = new CheckID();
                task.execute("http://" + MainActivity.IP_ADDRESS + "/IDcheck.php",IDcompare);
            }
        });

        //다 하고 완료 누르면 DB 에 넣는 부분
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID = IDEdittext.getText().toString();
                String PW = PWEdittext.getText().toString();
                String confirmPW = confirmPWEdittext.getText().toString();
                String nickname = nicknameEdittext.getText().toString();
                String phone = phoneEdittext.getText().toString();
                String user_type = user_type_selected.getText().toString();

                boolean checkConfirmPW;
                checkConfirmPW = PW.equals(confirmPW);

                if(ID.equals("") || PW.equals("") || confirmPW.equals("") || nickname.equals("") || phone.equals("")) {
                    Toast.makeText(RegistrationActivity.this, "Fill out the form", Toast.LENGTH_SHORT).show();
                } else {
                    if (checkConfirmPW) {
                        InsertData task = new InsertData();
                        task.execute("http://" + MainActivity.IP_ADDRESS + "/insert.php", ID, PW, nickname, user_type, phone);

                        IDEdittext.setText("");
                        PWEdittext.setText("");
                        confirmPWEdittext.setText("");
                        nicknameEdittext.setText("");
                        phoneEdittext.setText("");
                    } else {
                        Toast.makeText(RegistrationActivity.this, "Check PW again", Toast.LENGTH_SHORT).show();
                        confirmPWEdittext.setText("");
                    }
                }
            }
        });
    }


    class CheckID extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(RegistrationActivity.this,"Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            boolean sameID = false;

            String result_string = new String("You can use this ID");

            sameID = result.equals(result_string);

            progressDialog.dismiss();

            Toast.makeText(RegistrationActivity.this, result, Toast.LENGTH_SHORT).show();

            if(sameID)
                textResult.setText("true");
        }

        @Override
        protected String doInBackground(String... params) {

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
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString();
            } catch (Exception e) {
                return new String("Error: " + e.getMessage());
            }
        }
    }

    class InsertData extends AsyncTask<String, Void, String>{
            ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(RegistrationActivity.this,"Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Toast.makeText(RegistrationActivity.this,result,Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... params) {

            String ID = (String)params[1];
            String PW = (String)params[2];
            String nickname = (String)params[3];
            String user_type = (String)params[4];
            String phone = (String)params[5];

            String serverURL = (String)params[0];
            String postParameters = "ID=" + ID + "&PW=" + PW + "&nickname=" + nickname + "&user_type=" + user_type + "&phone=" + phone;

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
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString();
            } catch (Exception e) {
                return new String("Same ID exists !");
            }
        }
    }
}
