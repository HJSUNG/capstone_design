package csecau.capstone.homeseek;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class PostingTitle extends BaseAdapter{
    Context context;
    ArrayList<posting_list> list_itemArrayList;
    String profile_image;
    LayoutInflater inflater;

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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (inflater == null){
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null){
            convertView = inflater.inflate(R.layout.posting_list_item, parent, false);
        }

        PostHolder holder = new PostHolder(convertView);
        holder.title_textView.setText(list_itemArrayList.get(position).getTitle());
        holder.name_textView.setText(list_itemArrayList.get(position).getEstateid());
        holder.content_textView.setText(list_itemArrayList.get(position).getExplain());
        GlideClient.downloadimg(context, list_itemArrayList.get(position).getProfile_image(), holder.profile_imageView);
        return convertView;
    }
}