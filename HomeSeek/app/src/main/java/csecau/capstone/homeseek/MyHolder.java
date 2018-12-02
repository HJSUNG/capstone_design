package csecau.capstone.homeseek;

import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyHolder {
    TextView title_textView;
    ImageView profile_image;
    public MyHolder(View itemView){
        title_textView = (TextView)itemView.findViewById(R.id.title_text);
        profile_image = (ImageView)itemView.findViewById(R.id.profile_image);
    }
}
