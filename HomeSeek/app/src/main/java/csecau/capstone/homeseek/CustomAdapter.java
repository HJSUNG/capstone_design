package csecau.capstone.homeseek;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class CustomAdapter extends PagerAdapter {
    LayoutInflater inflater;

    public CustomAdapter(LayoutInflater inflater) {
        this.inflater=inflater;

    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=null;
        view= inflater.inflate(R.layout.view_image, null);
        ImageView img= (ImageView)view.findViewById(R.id.image_view);
        img.setImageResource(R.drawable.room_01+position);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View v, Object obj) {
        return v==obj;
    }
}

