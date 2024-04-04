package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Guest;

import java.util.List;

public interface ServeManager {

    void serveMeals(List<Guest> cycleGuests);

    void runSeason();
}

