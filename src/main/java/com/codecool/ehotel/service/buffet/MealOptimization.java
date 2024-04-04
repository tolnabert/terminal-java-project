package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.MealType;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import com.codecool.ehotel.service.logger.Logger;

public class MealOptimization implements BuffetConfigurator {

    private final Logger logger;

    public MealOptimization(Logger logger) {
        this.logger = logger;
    }

    @Override
    public LinkedHashMap<MealType, Integer> collectMealTypeCountsForDay(List<Guest> guestsForDay) {
        LinkedHashMap<MealType, Integer> mealTypeCounts = new LinkedHashMap<>();
        logger.logInfo("Collect guest preferred meals");

        for (Guest guest : guestsForDay) {
            List<MealType> guestMealPreferences = guest.guestType().getMealPreferences();

            for (MealType mealType : guestMealPreferences) {
                mealTypeCounts.put(mealType, mealTypeCounts.getOrDefault(mealType, 0) + 1);
            }
        }
        return optimizeMealTypeCountsForCycle(mealTypeCounts);
    }

    private LinkedHashMap<MealType, Integer> optimizeMealTypeCountsForCycle(LinkedHashMap<MealType, Integer> mealTypeCounts) {
        for (MealType mealTypeKey : mealTypeCounts.keySet()) {
            int currentQty = mealTypeCounts.get(mealTypeKey);
            mealTypeCounts.put(mealTypeKey, (currentQty + 15) / 15);
        }
        return mealTypeCounts;
    }
}
