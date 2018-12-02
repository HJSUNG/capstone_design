package csecau.capstone.homeseek;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PostHolder {
    TextView title_textView;
    ImageView profile_imageView;
    TextView name_textView;
    TextView content_textView;

    public PostHolder(View itemView){
        title_textView = (TextView) itemView.findViewById(R.id.title_text);
        profile_imageView = (ImageView) itemView.findViewById(R.id.profile_image);
        name_textView = (TextView)itemView.findViewById(R.id.nickname_textview);
        content_textView = (TextView)itemView.findViewById(R.id.content_textview);
    }
}
