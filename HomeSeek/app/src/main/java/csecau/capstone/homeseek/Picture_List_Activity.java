package csecau.capstone.homeseek;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class Picture_List_Activity extends Activity {
    ListView listView;
    PictureListAdapter pictureListAdapter;
    ArrayList<list_item> list_itemArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_list);
        listView = (ListView)findViewById(R.id.my_listview);

        list_itemArrayList =new ArrayList<list_item>();

        list_itemArrayList.add(
                new list_item(R.mipmap.ic_launcher,"닉네임1","제목1",
                        new Date(System.currentTimeMillis()),"내용1"));

        list_itemArrayList.add(
                new list_item(R.mipmap.ic_launcher,"닉네임2","제목2",
                        new Date(System.currentTimeMillis()),"내용2"));

        list_itemArrayList.add(
                new list_item(R.mipmap.ic_launcher,"닉네임3","제목3",
                        new Date(System.currentTimeMillis()),"내용3"));

        list_itemArrayList.add(
                new list_item(R.mipmap.ic_launcher,"닉네임4","제목4",
                        new Date(System.currentTimeMillis()),"내용4"));

        pictureListAdapter = new PictureListAdapter(Picture_List_Activity.this, list_itemArrayList);
        listView.setAdapter(pictureListAdapter);
    }
}
