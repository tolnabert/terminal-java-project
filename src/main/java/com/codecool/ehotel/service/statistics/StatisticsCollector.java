package com.codecool.ehotel.service.statistics;

public interface StatisticsCollector {

    void incrementNumberOfGuestOfSeason();

    void incrementPositiveExperience();

    void incrementNegativeExperience();

    void incrementWasteOfSeason(int wastedMeal);

    void incrementMealsPrepared();

    void incrementMealsConsumed();

    void summarizeCycle();

}
