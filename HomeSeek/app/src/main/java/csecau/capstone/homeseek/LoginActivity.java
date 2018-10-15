package csecau.capstone.homeseek;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private Button loginbutton, registrationbutton;
    private EditText idedittext, passwordedittext;
    private CheckBox checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginbutton = (Button) findViewById(R.id.loginButton);
        registrationbutton = (Button) findViewById(R.id.signupButton);
        idedittext = (EditText) findViewById(R.id.idinput);
        passwordedittext = (EditText) findViewById(R.id.passwordinput);
        checkbox = (CheckBox) findViewById(R.id.checkbox);

        registrationbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}
