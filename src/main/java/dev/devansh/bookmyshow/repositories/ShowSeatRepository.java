package dev.devansh.bookmyshow.repositories;

import dev.devansh.bookmyshow.models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

    @Override
    List<ShowSeat> findAllById(Iterable<Long> longs);

    @Override
    ShowSeat save(ShowSeat showSeat); //Update if passed ShowSeat entity has an id, else insert/create
}
