package csecau.capstone.homeseek;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
                    Intent intent = new Intent(getApplicationContext(), testposting.class);
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
