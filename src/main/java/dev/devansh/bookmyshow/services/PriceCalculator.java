package dev.devansh.bookmyshow.services;

import dev.devansh.bookmyshow.models.Show;
import dev.devansh.bookmyshow.models.ShowSeat;
import dev.devansh.bookmyshow.models.ShowSeatType;
import dev.devansh.bookmyshow.repositories.ShowSeatTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;

@Service
public class PriceCalculator {
    private ShowSeatTypeRepository showSeatTypeRepository;

    @Autowired
    public PriceCalculator(ShowSeatTypeRepository showSeatTypeRepository) {
        this.showSeatTypeRepository = showSeatTypeRepository;
    }


    public int calculatePrice(List<ShowSeat> seats, Show show) {
        //1. Get ShowSeatType for that show
        List<ShowSeatType> showSeatTypes = showSeatTypeRepository.findAllByShow(show);


        int amount = 0;

        //2. Get Seattype for all the seats
        for (ShowSeat showSeat: seats){
            for (ShowSeatType showSeatType: showSeatTypes){
                //3. Add amount of all
                if (showSeat.getSeat().getSeatType().equals(showSeatType.getSeatType())) {
                    amount+=showSeatType.getPrice();
                    break;
                }
            }
        }

        return amount;
    }
}
