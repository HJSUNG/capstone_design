package csecau.capstone.homeseek;

import android.content.Context;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<List> arrayList;
    private LayoutInflater inflater;
    private boolean isListView;
    private SparseBooleanArray mSelectedItem;

    public GridAdapter(Context context, ArrayList<List> arrayList, boolean isListView){
        this.context = context;
        this.arrayList = arrayList;
        this.isListView = isListView;
        inflater = LayoutInflater.from(context);
        mSelectedItem = new SparseBooleanArray();
    }

    @Override
    public int getCount(){
        return arrayList.size();
    }

    @Override
    public Object getItem(int i){
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i){
        return i;
    }

    private class ViewHolder{
        private TextView label;
        private CheckBox checkBox;
    }


    public void check_check(int position, boolean value){
        if(value) mSelectedItem.put(position, true);
        else mSelectedItem.delete(position);

        notifyDataSetChanged();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup){
        ViewHolder viewHolder;

        if(view==null){
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.search_grid, viewGroup, false);

            viewHolder.label = (TextView)view.findViewById(R.id.label);
            viewHolder.label.setText(arrayList.get(i).getText());
            viewHolder.checkBox = (CheckBox)view.findViewById(R.id.checkbox);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.label.setText(arrayList.get(i).getText());
        viewHolder.checkBox.setChecked(mSelectedItem.get(i));

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                check_check(i, !mSelectedItem.get(i));
            }
        });
        return view;
    }

    public SparseBooleanArray getSelectedIds(){
        return mSelectedItem;
    }



}
