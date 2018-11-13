package csecau.capstone.homeseek;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import csecau.capstone.homeseek.db.SimpleDB;
import csecau.capstone.homeseek.vo.ArticleVO;

public class DetailActivity extends AppCompatActivity {

    private TextView tvSubject;
    private TextView tvArticleNumber;
    private TextView tvAuthor;
    private TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);

        tvSubject = (TextView) findViewById(R.id.tvSubject);
        tvArticleNumber = (TextView) findViewById(R.id.tvArticleNumber);
        tvAuthor = (TextView) findViewById(R.id.tvAuthor);
        tvDescription = (TextView) findViewById(R.id.tvDescription);

        Intent intent = getIntent();
        String key = intent.getStringExtra("Key");

        ArticleVO articleVO = SimpleDB.getArticle(key);

        tvSubject.setText(articleVO.getSubject());
        tvArticleNumber.setText(articleVO.getArticleNo()+"");
        tvAuthor.setText(articleVO.getAuthor());
        tvDescription.setText(articleVO.getDescription());
    }
}
