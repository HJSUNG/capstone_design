package csecau.capstone.homeseek;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static csecau.capstone.homeseek.MainActivity.user;

public class LoginActivity extends AppCompatActivity {
    private static String TAG = "phptest";

    private Button loginButton;
    private Button registrationButton;

    private EditText IDEdittext;
    private EditText PWEdittext;

    private CheckBox checkbox;

    private TextView textResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText idinput = (EditText)findViewById(R.id.idinput);

        loginButton = (Button) findViewById(R.id.loginButton);
        registrationButton = (Button) findViewById(R.id.signupButton);

        IDEdittext = (EditText) findViewById(R.id.idinput);
        PWEdittext = (EditText) findViewById(R.id.passwordinput);

        checkbox = (CheckBox) findViewById(R.id.checkbox);

        textResult = (TextView) findViewById(R.id.TextResultLogin);

        registrationButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID = IDEdittext.getText().toString();
                String PW = PWEdittext.getText().toString();
//                String item_num="";

                Login task = new Login();
                task.execute("http://" + MainActivity.IP_ADDRESS + "/login.php", ID, PW);

//                Bookmark bookmark_task = new Bookmark();
//                bookmark_task.execute("http://" + MainActivity.IP_ADDRESS + "/bookmark.php", ID);
//                Delete_bookmark delete_bookmark_task = new Delete_bookmark();
//                Delete_bookmark_task.execute("http://" + MainActivity.IP_ADDRESS + "/delete_bookmark.php", ID, item_num);

            }
        });
    }

    class Login extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = progressDialog.show(LoginActivity.this, "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            String result_string[] = (String[]) result.split(",");

            if(result_string.length !=4) {
                Toast.makeText(LoginActivity.this, "Log-in failed", Toast.LENGTH_SHORT).show();
            } else {
                user.log_in(result_string[0], result_string[1], result_string[2], result_string[3]);
                Toast.makeText(LoginActivity.this, "Log-in Success", Toast.LENGTH_SHORT).show();

                Log.d("test",user.info_ID);

                Intent intent = new Intent(getApplicationContext(), navigation_main.class);
                startActivity(intent);
            }
        }

        @Override
        protected String doInBackground(String...params) {
            String ID = (String)params[1];
            String PW = (String)params[2];

            String serverURL = (String)params[0];
            String postParameters = "ID=" + ID + "&PW=" + PW;

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
            String result_string_not_concat = result;
            String result_string[] = (String[]) result.split(",");

            if(result_string[0].length() != 2) {
                Toast.makeText(LoginActivity.this, "No bookmark", Toast.LENGTH_SHORT).show();
            } else {
                textResult.setText(result_string_not_concat);
            }
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

    class Delete_bookmark extends AsyncTask<String, Void, String>{
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
            Toast.makeText(LoginActivity.this, "Delete done !", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String...params) {
            String ID = (String)params[1];
            String item_num = (String)params[2];

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
}