package csecau.capstone.homeseek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PostingTitle extends BaseAdapter{
    Context context;
    ArrayList<posting_list> list_itemArrayList;

    public PostingTitle(Context context, ArrayList<posting_list> list_itemArrayList) {
        this.context = context;
        this.list_itemArrayList = list_itemArrayList;
    }

    @Override
    public int getCount() {
        return this.list_itemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list_itemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    TextView title_textView;
    ImageView profile_imageView;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.posting_list_item,null);
            title_textView = (TextView) convertView.findViewById(R.id.title_text);
            profile_imageView = (ImageView) convertView.findViewById(R.id.profile_image);
        }

        title_textView.setText(list_itemArrayList.get(position).getTitle());
        profile_imageView.setImageResource(list_itemArrayList.get(position).getProfile_image());

        return  convertView;
    }
}
