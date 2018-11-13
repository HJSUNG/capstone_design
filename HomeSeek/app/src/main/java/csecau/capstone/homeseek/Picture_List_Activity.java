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
                new list_item(R.drawable.room_01,"중앙대원룸","정문 앞 좋은 원룸이요","보증금 500에 월세 35 받습니다.\n관리비는 2만원이에요"));

        list_itemArrayList.add(
                new list_item(R.drawable.room_02,"흑석원룸","정문 앞 좋은 원룸이요","보증금 500에 월세 35 받습니다."));

        list_itemArrayList.add(
                new list_item(R.drawable.room_03,"닉네임2","제목2","내용2"));

        list_itemArrayList.add(
                new list_item(R.drawable.room_04,"닉네임3","제목3", "내용3"));

        list_itemArrayList.add(
                new list_item(R.drawable.room_05,"닉네임4","제목4", "내용4"));

        list_itemArrayList.add(
                new list_item(R.drawable.room_05,"닉네임","제목", "내용"));


        pictureListAdapter = new PictureListAdapter(Picture_List_Activity.this, list_itemArrayList);
        listView.setAdapter(pictureListAdapter);
    }
}
