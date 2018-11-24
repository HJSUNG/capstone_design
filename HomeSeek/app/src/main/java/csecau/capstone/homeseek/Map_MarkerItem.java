package csecau.capstone.homeseek;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class Map_MarkerItem implements ClusterItem{


    double lat;
    double lon;
    LatLng mPosition;
    int price;
    int index;

    public Map_MarkerItem(double lat, double lon, int price, int index) {
        mPosition = new LatLng(lat, lon);
        this.price = price;
        this.index= index;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public int getPrice() {
        return price;
    }

    public int getIndex(){return index;}

    @Override
    public LatLng getPosition() {
        return mPosition;
    }
}
