package csecau.capstone.homeseek;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

public class RegistrationActivity extends AppCompatActivity {

    private static String IP_ADDRESS = "223.62.8.124";
    private static String TAG = "phptest";

    private EditText IDEdittext;
    private EditText PWEdittext;
    private EditText nicknameEdittext;
    private Button checkButton;
    private Button doneButton;
    private TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        IDEdittext = (EditText)findViewById(R.id.IDregister);
        PWEdittext = (EditText)findViewById(R.id.PWregister);
        nicknameEdittext = (EditText)findViewById(R.id.NicknameRegister);

        checkButton = (Button)findViewById(R.id.IDcheck);
        doneButton = (Button)findViewById(R.id.DoneRegister);

        textResult = (TextView)findViewById(R.id.TextResultRegister);

        doneButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){

                String ID = IDEdittext.getText().toString();
                String PW = PWEdittext.getText().toString();
                String nickname = nicknameEdittext.getText().toString();

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/insert.php", ID,PW,nickname);

                IDEdittext.setText("");
                PWEdittext.setText("");
                nicknameEdittext.setText("");
            }
        });
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
            textResult.setText(result);
            Log.d(TAG, "POST response  - " + result);
        }

        @Override
        protected String doInBackground(String... params) {

            String ID = (String)params[1];
            String PW = (String)params[2];
            String nickname = (String)params[3];

            String serverURL = (String)params[0];
            String postParameters = "ID=" + ID + "&PW=" + PW + "&nickname=" + nickname;

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
                Log.d(TAG, "InsertData: Error ", e);
                return new String("Error: " + e.getMessage());
            }
        }
    }
}
