package dev.devansh.bookmyshow.services;

import dev.devansh.bookmyshow.models.Booking;
import dev.devansh.bookmyshow.repositories.BookingRepository;
import dev.devansh.bookmyshow.repositories.ShowRepository;
import dev.devansh.bookmyshow.repositories.ShowSeatRepository;
import dev.devansh.bookmyshow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {
    private BookingRepository bookingRepository;
    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository,
                          UserRepository userRepository,
                          ShowRepository showRepository,
                          ShowSeatRepository showSeatRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
    }



    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookMovie(Long userId, List<Long> seatIds, Long showId) {
        //In our implementation, we'll acquire the lock here
        //1. Get user with that userId
        //2. Get show with that showID
        //---------IDEALLY HERE TAKE LOCK-------------
        //3. Get ShowSeat with the seatIds
        //4. Check if all seats are available
        //5. If no, throw error
        //6. If yes, mark the status of show seats as LOCKED
        //7. Save updated show seats to db
        //--------IDEALLY HERE RELEASE LOCK-----------
        //8. Return
        //In our implementation, we'll release the lock here


        return null;
    }
}
