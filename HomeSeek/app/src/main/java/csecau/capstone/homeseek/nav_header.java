package csecau.capstone.homeseek;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


import static csecau.capstone.homeseek.MainActivity.user;

public class nav_header extends AppCompatActivity {
    public static TextView ID_textview;
    public static TextView nickname_textview;
    public static TextView phone_textview;
    public static TextView type_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header_main);

        ID_textview = (TextView) findViewById(R.id.user_ID);
        nickname_textview = (TextView) findViewById(R.id.user_nickname);
        phone_textview = (TextView) findViewById(R.id.user_phone);
        type_textview = (TextView) findViewById(R.id.user_type);

        ID_textview.setText(user.info_ID);
        nickname_textview.setText(user.info_nickname);
        phone_textview.setText(user.info_phone);
        type_textview.setText(user.info_type);
    }
}
