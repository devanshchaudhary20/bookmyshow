package dev.devansh.bookmyshow.controllers;

import dev.devansh.bookmyshow.dtos.BookMovieRequestDto;
import dev.devansh.bookmyshow.dtos.BookMovieResponseDto;
import dev.devansh.bookmyshow.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {

    private BookingService bookingService;

    @Autowired // Automatically find an object of the params, create it and send here
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public BookMovieResponseDto bookTicket(BookMovieRequestDto request) {
        return null;
    }
}
