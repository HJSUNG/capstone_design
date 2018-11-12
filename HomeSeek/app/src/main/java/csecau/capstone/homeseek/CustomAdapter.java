package csecau.capstone.homeseek;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomAdapter extends PagerAdapter {
    Context context;
    ArrayList<String> data;

    public CustomAdapter(Context context, ArrayList<String> data) {
        this.context=context;
        this.data = data;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= inflater.inflate(R.layout.view_image, null);
        ImageView img= (ImageView)view.findViewById(R.id.image_view);
        Glide.with(context).load(data.get(position)).into(img);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View v, Object obj) {
        return v==obj;
    }
}

