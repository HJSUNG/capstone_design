package csecau.capstone.homeseek;

public class Map_MarkerItem {


    double lat;
    double lon;
    int price;

    public Map_MarkerItem(double lat, double lon, int price) {
        this.lat = lat;
        this.lon = lon;
        this.price = price;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


}
