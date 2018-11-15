package csecau.capstone.homeseek;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

public class Grid_view extends Fragment{
    private Context context;
    private GridAdapter adapter;
    private Button mapButton, searchButton, subwayButton;
    ArrayList<List> arrayList;
    int[] selected = new int[6];

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

    public int[] getSelected(){
        return selected;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        mapButton = (Button)view.findViewById(R.id.map_button);
        searchButton = (Button)view.findViewById(R.id.searchButton);
        subwayButton = (Button)view.findViewById(R.id.subway_button);
        loadGridView(view);
        onClickEvent(view);
    }

    public void addItem(String text){
        List list_item = new List();
        list_item.setText(text);

        arrayList.add(list_item);
    }

    private void onClickEvent(View view){
        view.findViewById(R.id.map_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        view.findViewById(R.id.searchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray selectedRows = adapter.getSelectedIds();
                if(selectedRows.size()>0){
                    for (int i = 0; i < selectedRows.size(); i++) {
                        if (selectedRows.valueAt(i)) {
                            String selectedRowLabel = arrayList.get(selectedRows.keyAt(i)).getText();
                            switch (selectedRowLabel){
                                case "세탁기":
                                    selected[0]= 1;
                                    break;
                                case "냉장고":
                                    selected[1] = 1;
                                    break;
                                case "책상":
                                    selected[2] = 1;
                                    break;
                                case "침대":
                                    selected[3] = 1;
                                    break;
                                case "전자레인지":
                                    selected[4] = 1;
                                    break;
                                case "옷장":
                                    selected[5] = 1;
                                    break;
                            }
                        }
                    }
                    Intent intent = new Intent(context,testposting.class);
                    intent.putExtra("selected", selected);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(context,testposting.class);
                    startActivity(intent);
                }
            }
        });

        view.findViewById(R.id.subway_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void loadGridView(View view){
        GridView gridView = (GridView)view.findViewById(R.id.grid_view);
        arrayList = new ArrayList<List>();
        addItem("세탁기");
        addItem("냉장고");
        addItem("책상");
        addItem("침대");
        addItem("전자레인지");
        addItem("옷장");
        adapter = new GridAdapter(context, arrayList, false);
        gridView.setAdapter(adapter);

    }
}
