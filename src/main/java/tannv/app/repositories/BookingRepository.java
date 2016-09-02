package tannv.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import tannv.app.entities.HotelBooking;

import java.util.List;

/**
 * Created by nguyen.van.tan on 8/31/16.
 */
@Repository
public interface BookingRepository extends JpaRepository<HotelBooking,Long> {

    List<HotelBooking> findByNumberOfNightGreaterThan(@PathVariable int numberOfNight);

}
