package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.MealType;

import java.util.*;

public class BuffetConfiguratorBreakfast implements BuffetConfigurator {

    LinkedHashMap<MealType, Integer> maximumPortionsByMealType;

    public BuffetConfiguratorBreakfast() {
        this.maximumPortionsByMealType = generateMaximumPortionsByMealType();
    }

    private LinkedHashMap<MealType, Integer> generateMaximumPortionsByMealType() {
        //TODO: Get guest for the actual day, count preferred meals
        // configure Buffet accordingly to it with a new algorithm.
        LinkedHashMap<MealType, Integer> maximumPortionsByMealType = new LinkedHashMap<>();
        MealType[] mealTypes = MealType.values();
        for (MealType mealType : mealTypes) {
            maximumPortionsByMealType.put(mealType, 3);
        }
        return maximumPortionsByMealType;
    }


    @Override
    public LinkedHashMap<MealType, Integer> collectMealTypeCountsForDay(List<Guest> cycleGuests) {
        return null;
    }
}
