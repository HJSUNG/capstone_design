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
                new list_item(R.drawable.room_03,"중앙대원룸","후문 코앞 원룸","후문 정류장 바로 윗집"));

        list_itemArrayList.add(
                new list_item(R.drawable.room_04,"중앙대원룸","후문 도보 5분거리", "호텔 같은 자취방"));

        list_itemArrayList.add(
                new list_item(R.drawable.room_05,"흑석원룸","신축 좋은 방 있습니다.", "신축이라 깨끗합니다."));

//        list_itemArrayList.add(
//                new list_item(R.drawable.room_06,"중앙대자취방","신축원룸 월세,관리비포함 55요", "신축에 풀옵션이에요"));
//
//        list_itemArrayList.add(
//                new list_item(R.drawable.room_07,"중앙대자취방","신축원룸 전세로도 가능합니다.", "1000/60 또는 전세로 8000"));


        pictureListAdapter = new PictureListAdapter(Picture_List_Activity.this, list_itemArrayList);
        listView.setAdapter(pictureListAdapter);
    }
}
