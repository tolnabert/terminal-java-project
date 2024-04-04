package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.MealType;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public interface BuffetConfigurator {

    LinkedHashMap<MealType, Integer> collectMealTypeCountsForDay(List<Guest> cycleGuests);

}
