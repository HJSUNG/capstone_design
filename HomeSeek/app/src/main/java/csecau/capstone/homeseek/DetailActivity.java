package csecau.capstone.homeseek;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import csecau.capstone.homeseek.db.SimpleDB;
import csecau.capstone.homeseek.vo.ArticleVO;

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
