package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.MealType;
import com.codecool.ehotel.service.logger.Logger;
import com.codecool.ehotel.service.statistics.StatisticsCollector;

import java.time.LocalDate;
import java.util.*;

public class BuffetServiceImpl implements BuffetService {

    private final Buffet buffet;
    private int cycleCounter;
    private final int maxCycle;
    private static final Random RANDOM = new Random();
    private final Logger logger;
    private final StatisticsCollector statisticsCollector;

    public BuffetServiceImpl(int maxCycle, Logger logger, StatisticsCollector statisticsCollector) {
        this.buffet = new Buffet(statisticsCollector);
        this.cycleCounter = 0;
        this.maxCycle = maxCycle;
        this.logger = logger;
        this.statisticsCollector = statisticsCollector;
    }

    @Override
    public void runCycle(List<Guest> guestForCurrentCycle) {
        summarizeBuffetStatus();
        refill();
        feedGuests(guestForCurrentCycle);
        calculateWaste();
    }

    private void summarizeBuffetStatus() {
        for (MealType mealType : MealType.values()) {
            logger.logInfo("Remaining " + mealType.name().toLowerCase() + ": " + buffet.getMealCountByType(mealType) + "/" + buffet.getMaxMealCountByType(mealType));
        }

    }

    @Override
    public void initializeBuffet(LinkedHashMap<MealType, Integer> maximumPortionsByMealType) {
        logger.logInfo("Initializing buffet accordingly to daily guest anticipation");
        this.buffet.setMaximumPortionsByMealType(maximumPortionsByMealType);
    }

    private void feedGuests(List<Guest> guestForCurrentCycle) {
        logger.logInfo("The breakfast has began!");
        for (Guest guest : guestForCurrentCycle) {
            List<MealType> preferredMeals = guest.guestType().getMealPreferences();
            MealType selectedMeal = preferredMeals.get(RANDOM.nextInt(preferredMeals.size()));
            boolean guestSatisfaction = consumeFreshest(selectedMeal);
            String logMessage = guestSatisfaction
                    ? "Guest is happy!"
                    : "Guest is NOT happy, " + selectedMeal + " is out!";
            logger.logInfo(logMessage);
        }
    }

    @Override
    public Buffet refill() {
        logger.logInfo("Refilling buffet");
        buffet.refill(cycleCounter);
        return null;
    }


    @Override
    public boolean consumeFreshest(MealType mealType) {
        logger.logInfo("Guest is trying to eat a " + mealType.name().toLowerCase());
        boolean isEaten = buffet.consumeFreshest(mealType);
        if (isEaten) {
            statisticsCollector.incrementPositiveExperience();
            statisticsCollector.incrementMealsConsumed();//future implementation of guest eating not favored meal
        } else {
            statisticsCollector.incrementNegativeExperience();
        }
        return isEaten;
    }

    @Override
    public void calculateWaste() {
        int cycleWaste = buffet.calculateWaste(cycleCounter, maxCycle);
        logger.logInfo("Spoiled waste after " + cycleCounter + " breakfast cycle: " + cycleWaste);
        this.cycleCounter++;
        statisticsCollector.incrementWasteOfSeason(cycleWaste);
        //TODO for test purpose to check if at the end of season buffet is empty (including long)
        if (cycleCounter + 1 == maxCycle) {
            summarizeBuffetStatus();
        }
    }
}


