package kz.elaman.gazservice.model;

/**
 * Created by Myrzabek on 08/04/2017.
 */

public class Indicate {
    private String date, price;

    public Indicate(String date, String price) {
        this.date = date;
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public String getPrice() {
        return price;
    }
}
