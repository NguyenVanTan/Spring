package tannv.app.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by nguyen.van.tan on 8/31/16.
 */

@Entity
public class HotelBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String hottelName;
    private double price;
    private int numberOfNight;

    public HotelBooking()
    {

    }
   public HotelBooking (String hottelName, double price, int numberOfNight)
   {
       this.hottelName=hottelName;
       this.price=price;
       this.numberOfNight=numberOfNight;


   }

    public String getHottelName() {
        return hottelName;
    }

    public void setHottelName(String hottelName) {
        this.hottelName = hottelName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumberOfNight() {
        return numberOfNight;
    }

    public void setNumberOfNight(int numberOfNight) {
        this.numberOfNight = numberOfNight;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
