package com.codecool.ehotel.service.statistics;

public class StatisticsCollectorImpl implements StatisticsCollector {

    private int numberOfGuestOfSeason;
    private int positiveExperience;
    private int negativeExperience;
    private int wasteOfSeason;
    private int mealsPrepared;
    private int mealsConsumed;

    @Override
    public String toString() {
        return "In this season we had " + numberOfGuestOfSeason + " guests" +
                ", positive experiences: " + positiveExperience +
                ", negative experiences: " + negativeExperience +
                ", number of meals prepared: " + mealsPrepared +
                ", number of meals consumed: " + mealsConsumed +
                ", total waste: " + wasteOfSeason + " Codecool coin";
    }

    @Override
    public void incrementNumberOfGuestOfSeason() {
        numberOfGuestOfSeason++;
    }

    @Override
    public void incrementPositiveExperience() {
        positiveExperience++;
    }

    @Override
    public void incrementNegativeExperience() {
        negativeExperience++;
    }

    @Override
    public void incrementWasteOfSeason(int wastedMeal) {
         wasteOfSeason+=wastedMeal;
    }

    @Override
    public void incrementMealsPrepared() {
         mealsPrepared++;
    }

    @Override
    public void incrementMealsConsumed() {
        mealsConsumed++;
    }

    @Override
    public void summarizeCycle() {

    }
}
