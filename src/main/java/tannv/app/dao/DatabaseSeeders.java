package tannv.app.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tannv.app.entities.HotelBooking;
import tannv.app.repositories.BookingRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguyen.van.tan on 9/1/16.
 */
@Component
public class DatabaseSeeders implements CommandLineRunner {
    private BookingRepository bookingRepository;


    @Autowired
    public DatabaseSeeders(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        List<HotelBooking>
                hotelBookingList = new ArrayList<HotelBooking>();

        hotelBookingList.add(new HotelBooking("Marrior", 1000f, 2));
        hotelBookingList.add(new HotelBooking("Tesst", 100f, 1));
        hotelBookingList.add(new HotelBooking("Tesss", 10f, 3));
        bookingRepository.save(hotelBookingList);

    }
}
