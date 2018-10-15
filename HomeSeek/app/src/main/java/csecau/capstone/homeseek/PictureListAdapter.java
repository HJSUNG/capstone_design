package csecau.capstone.homeseek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PictureListAdapter extends BaseAdapter {
    Context context;
    ArrayList<list_item> list_itemArrayList;
    ViewHolder viewholder;

    public PictureListAdapter(Context context, ArrayList<list_item> list_itemArrayList) {
        this.context = context;
        this.list_itemArrayList = list_itemArrayList;
    }



        @Override
        public int getCount() {
            return this.list_itemArrayList.size();
        }
        /*
        int getCount()는 이 리스트뷰가 몇개의 아이템을 가지고 있는지를 알려주는 함수
         */

        @Override
        public Object getItem(int position) {
            return this.list_itemArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView = LayoutInflater.from(context).inflate(R.layout.picture_list_item,null);
                viewholder = new ViewHolder();
                viewholder.nickname_textView = (TextView) convertView.findViewById(R.id.nickname_textview);
                viewholder.content_textView = (TextView) convertView.findViewById(R.id.content_textview);
                viewholder.date_textView = (TextView) convertView.findViewById(R.id.date_textview);
                viewholder.title_textView = (TextView) convertView.findViewById(R.id.title_textview);
                viewholder.profile_imageView = (ImageView) convertView.findViewById(R.id.profile_imageview);
            }

            viewholder.nickname_textView.setText(list_itemArrayList.get(position).getNickname());
            viewholder.title_textView.setText(list_itemArrayList.get(position).getTitle());
            viewholder.content_textView.setText(list_itemArrayList.get(position).getContent());
            viewholder.date_textView.setText(list_itemArrayList.get(position).getWrite_date().toString());


            return  convertView;
        }

        class ViewHolder{
            TextView nickname_textView;
            TextView title_textView;
            TextView date_textView;
            TextView content_textView;
            ImageView profile_imageView;
        }
    }


