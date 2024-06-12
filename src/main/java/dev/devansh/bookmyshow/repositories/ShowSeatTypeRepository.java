package dev.devansh.bookmyshow.repositories;

import dev.devansh.bookmyshow.models.Show;
import dev.devansh.bookmyshow.models.ShowSeat;
import dev.devansh.bookmyshow.models.ShowSeatType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowSeatTypeRepository extends JpaRepository<ShowSeatType, Long> {

    List<ShowSeatType> findAllByShow(Show show);
}
