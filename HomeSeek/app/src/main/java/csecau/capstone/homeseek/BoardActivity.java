package csecau.capstone.homeseek;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import csecau.capstone.homeseek.db.SimpleDB;
import csecau.capstone.homeseek.vo.ArticleVO;

public class BoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        prepareSimpleDB();

        LinearLayout ll = (LinearLayout) findViewById(R.id.itemList);

        for (int i=0; i< SimpleDB.getIndexes().size(); i++) {
            Button button = new AppCompatButton(this);
            button.setText(SimpleDB.getIndexes().get(i));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DetailActivity.class);
                    String buttonText = (String) ((Button) view).getText();

                    intent.putExtra("Key", buttonText);
                    startActivity(intent);
                }
            });

            ll.addView(button);
        }
    }

    private  void  prepareSimpleDB() {

        for (int i=1; i<13; i++){
            SimpleDB.addArticle(i + "번글", new ArticleVO(i,i+"번글 제목",i +"번글 내용","ID : 누구"));
        }
    }
}
