package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.SeasonType;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public interface GuestService {

    Guest generateRandomGuest(SeasonType seasonType, LocalDate seasonStart, LocalDate seasonEnd);

    List<Guest> generateRandomGuests(SeasonType seasonType, LocalDate seasonStart, LocalDate seasonEnd);

    List<Guest> getGuestsForToday(List<Guest> guests, LocalDate date);

    HashMap<Integer, List<Guest>> splitGuestsForDay(List<Guest> guestsForToday, int cycleCount);

    List<Guest> getGuestsForCycle(int cycleKey);

}
