package csecau.capstone.homeseek;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class Map_MarkerItem implements ClusterItem{


    double lat;
    double lon;
    LatLng mPosition;
    int price;

    public Map_MarkerItem(double lat, double lon, int price) {
        mPosition = new LatLng(lat, lon);
        this.price = price;
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


    @Override
    public LatLng getPosition() {
        return mPosition;
    }
}
