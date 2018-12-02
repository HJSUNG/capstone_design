package csecau.capstone.homeseek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BoardComment extends BaseAdapter {
    Context context;
    ArrayList<comment_list> list_itemArrayList;
    TextView id_tv, title_tv;
    LayoutInflater inflater;

    public BoardComment(Context context, ArrayList<comment_list> list_itemArrayList) {
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
            convertView = inflater.inflate(R.layout.activity_board_detail, parent, false);
            id_tv = convertView.findViewById(R.id.id_tv_comment);
            title_tv = convertView.findViewById(R.id.title_tv_comment);
        }

        title_tv.setText(list_itemArrayList.get(position).getDetail());
        id_tv.setText(list_itemArrayList.get(position).getId());

        return convertView;
    }
}