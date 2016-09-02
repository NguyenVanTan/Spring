package tannv.app.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tannv.app.entities.HotelBooking;
import tannv.app.repositories.BookingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nguyen.van.tan on 8/31/16.
 */


@RestController
@RequestMapping("/booking")
public class BookingController {

    BookingRepository bookingRepository;

    @Autowired
    public BookingController(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;

    }


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<HotelBooking> getAll() {

        return bookingRepository.findAll();
    }


    @RequestMapping(value = "affortable/{price}", method = RequestMethod.GET)
    public List<HotelBooking> getAffortabe(@PathVariable double price) {
        return bookingRepository.findAll().stream().filter(t -> t.getPrice() >= price).collect(Collectors.toList());

    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public List<HotelBooking> createBooking(@RequestBody HotelBooking boooking) {
        bookingRepository.save(boooking);
        return bookingRepository.findAll();
    }
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public List<HotelBooking> deleteBooking(@PathVariable long id){
     bookingRepository.delete(id);
        return  bookingRepository.findAll();
    }

}
