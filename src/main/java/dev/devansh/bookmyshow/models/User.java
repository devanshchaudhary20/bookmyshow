package dev.devansh.bookmyshow.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User extends BaseModel{
    private String name;
    private String email;
    private List<Booking> bookings;
}
