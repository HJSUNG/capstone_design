package csecau.capstone.homeseek;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

public class Grid_view extends Fragment{
    private Context context;
    private GridAdapter adapter;
    ArrayList<List> arrayList;

    public Grid_view(){}

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.search_grid_check,container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        loadGridView(view);
    }

    public void addItem(String text){
        List list_item = new List();
        list_item.setText(text);

        arrayList.add(list_item);
    }

    private void loadGridView(View view){
        GridView gridView = (GridView)view.findViewById(R.id.grid_view);
        arrayList = new ArrayList<List>();
        addItem("냉장고");
        addItem("세탁기");
        addItem("신발장");
        addItem("책상");
        addItem("옷장");
        addItem("침대");
        adapter = new GridAdapter(context, arrayList, false);
        gridView.setAdapter(adapter);

    }


}
