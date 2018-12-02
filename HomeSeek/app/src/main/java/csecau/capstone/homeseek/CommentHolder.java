package csecau.capstone.homeseek;

import android.view.View;
import android.widget.TextView;

public class CommentHolder {
    TextView name_textView;
    TextView content_textView;

    public CommentHolder(View itemView){
        name_textView = (TextView)itemView.findViewById(R.id.nickname_textview);
        content_textView = (TextView)itemView.findViewById(R.id.content_textview);
    }
}
