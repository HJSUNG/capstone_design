package csecau.capstone.homeseek;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MypageActivity extends AppCompatActivity{

    public static User_information user;

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
                Intent intent = new Intent(getApplicationContext(), UploadActivity.class);
                startActivity(intent);
            }
        });
        favorite_supervise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UploadActivity.class);
                startActivity(intent);
            }
        });
        ID_supervise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UploadActivity.class);
                startActivity(intent);
            }
        });

    }

}
