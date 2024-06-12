package dev.devansh.bookmyshow.services;

import dev.devansh.bookmyshow.exceptions.ShowNotFoundException;
import dev.devansh.bookmyshow.exceptions.UserNotFoundException;
import dev.devansh.bookmyshow.models.*;
import dev.devansh.bookmyshow.repositories.BookingRepository;
import dev.devansh.bookmyshow.repositories.ShowRepository;
import dev.devansh.bookmyshow.repositories.ShowSeatRepository;
import dev.devansh.bookmyshow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private BookingRepository bookingRepository;
    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private PriceCalculator priceCalculator;

    @Autowired
    public BookingService(BookingRepository bookingRepository,
                          UserRepository userRepository,
                          ShowRepository showRepository,
                          ShowSeatRepository showSeatRepository,
                          PriceCalculator priceCalculator) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.priceCalculator = priceCalculator;
    }



    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookMovie(Long userId, List<Long> seatIds, Long showId) throws UserNotFoundException, ShowNotFoundException {
        //In our implementation, we'll acquire the lock here
        //1. Get user with that userId
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()){
            throw new UserNotFoundException();
        }

        User bookedBy = userOptional.get();

        Optional<Show> showOptional = showRepository.findById(showId);
        if (showOptional.isEmpty()){
            throw new ShowNotFoundException();
        }

        //2. Get show with that showID
        Show bookedShow = showOptional.get();

        //---------IDEALLY HERE TAKE LOCK-------------
        //3. Get ShowSeat with the seatIds
        List<ShowSeat> showSeats = showSeatRepository.findAllById(seatIds);

//        boolean allAvailableSeats = true;

        //4. Check if all seats are available
        //5. If no, throw error
        for (ShowSeat showSeat : showSeats) {
            //                allAvailableSeats = false;
            if (!(showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)) ||
            (showSeat.getShowSeatStatus().equals(ShowSeatStatus.BLOCKED) &&
                    Duration.between(showSeat.getBlockedAt().toInstant(), new Date().toInstant()).toMinutes() > 15
            )) {
                throw new RuntimeException();
            }
        }

        List<ShowSeat> savedShowSeats = new ArrayList<>();

        //6. If yes, mark the status of show seats as LOCKED

        for (ShowSeat showSeat : showSeats) {
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            //7. Save updated show seats to db
            savedShowSeats.add(showSeatRepository.save(showSeat));
        }

        //--------IDEALLY HERE RELEASE LOCK-----------
        //8. Create corresponding booking object

        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setShowSeats(savedShowSeats);
        booking.setUser(bookedBy);
        booking.setShow(bookedShow);
        booking.setBookedAt(new Date());
        booking.setAmount(priceCalculator.calculatePrice(savedShowSeats, bookedShow));
        booking.setPayments(new ArrayList<>());


        //9. Return booking object
        //In our implementation, we'll release the lock here
        Booking savedBooking = bookingRepository.save(booking);
        return savedBooking;
    }
}
