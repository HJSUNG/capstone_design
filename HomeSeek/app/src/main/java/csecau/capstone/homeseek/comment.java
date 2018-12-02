package csecau.capstone.homeseek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class comment extends BaseAdapter {
    Context context;
    ArrayList<comment_list> list_itemArrayList;
    String profile_image;
    LayoutInflater inflater;

    public comment(Context context, ArrayList<comment_list> list_itemArrayList) {
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (inflater == null){
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null){
            convertView = inflater.inflate(R.layout.comment_list_item, parent, false);
        }

        CommentHolder holder = new CommentHolder(convertView);
        holder.name_textView.setText(list_itemArrayList.get(position).getId());
        holder.content_textView.setText(list_itemArrayList.get(position).getDetail());
        return convertView;
    }
}