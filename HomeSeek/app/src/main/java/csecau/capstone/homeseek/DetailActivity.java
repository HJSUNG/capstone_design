package csecau.capstone.homeseek;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dovicvi.home_test.db.SimpleDB;
import com.example.dovicvi.home_test.vo.ArticleVO;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);

        Intent intent = getIntent();
        String key = intent.getStringExtra("Key");

        ArticleVO articleVO = SimpleDB.getArticle(key);
    }
}
