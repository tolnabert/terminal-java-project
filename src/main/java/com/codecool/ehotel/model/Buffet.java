package com.codecool.ehotel.model;

import com.codecool.ehotel.service.buffet.Meal;
import com.codecool.ehotel.service.buffet.MealImpl;
import com.codecool.ehotel.service.statistics.StatisticsCollector;

import java.util.*;

public class Buffet {
    private List<Stack<Meal>> availableMeals;
    private LinkedHashMap<MealType, Integer> maximumPortionsByMealType;
    private final StatisticsCollector statisticsCollector;

    public Buffet(StatisticsCollector statisticsCollector) {
        this.statisticsCollector = statisticsCollector;
    }

    public void setMaximumPortionsByMealType(LinkedHashMap<MealType, Integer> maximumPortionsByMealType) {
        this.maximumPortionsByMealType = maximumPortionsByMealType;
        this.availableMeals = generateEmptyMealStacks();
    }

    private List<Stack<Meal>> generateEmptyMealStacks() {
        List<Stack<Meal>> emptyMealStacks = new ArrayList<>();
        for (int i = 0; i < maximumPortionsByMealType.size(); i++) {
            emptyMealStacks.add(new Stack<>());
        }
        return emptyMealStacks;
    }

    public void refill(int cycle) {
        Set<MealType> mealTypeKeys = maximumPortionsByMealType.keySet();
        MealType[] mealTypesArr = mealTypeKeys.toArray(new MealType[0]);

        for (int i = 0; i < mealTypesArr.length; i++) {
            MealType currentMealType = mealTypesArr[i];
            Integer currentMealTypeMaximumPortion = maximumPortionsByMealType.get(currentMealType);
            Stack<Meal> currentMealQueue = availableMeals.get(i);

            if (currentMealTypeMaximumPortion != currentMealQueue.size()) {
                int difference = currentMealTypeMaximumPortion - currentMealQueue.size();

                for (int j = 0; j < difference; j++) {
                    currentMealQueue.add(new MealImpl(cycle, currentMealType));
                    statisticsCollector.incrementMealsPrepared();
                }
            }
        }
    }

    public boolean consumeFreshest(MealType mealType) {
        Stack<Meal> specificMealStack = getMealStackByType(mealType);
        if (!specificMealStack.isEmpty()) {
            specificMealStack.pop();
            return true;
        }
        return false;
    }

    private Stack<Meal> getMealStackByType(MealType mealType) {
        Set<MealType> mealTypeKeys = maximumPortionsByMealType.keySet();
        MealType[] mealTypesArr = mealTypeKeys.toArray(new MealType[0]);
        List<MealType> mealTypesList = List.of(mealTypesArr);
        int indexOfMealType = mealTypesList.indexOf(mealType);
        return availableMeals.get(indexOfMealType);
    }

    public List<Stack<Meal>> getAvailableMeals() {
        return availableMeals;
    }

    public int calculateWaste(int currentCycle, int maxCycle) {
        int totalWasteInCycle = 0;
        List<Stack<Meal>> newAvailableMeals = new ArrayList<>();
        for (Stack<Meal> availableMeal : availableMeals) {
            List<Meal> mealsList = new ArrayList<>(availableMeal);
            List<Meal> mealsToRemove = new ArrayList<>();
            for (Meal meal : mealsList) {
                if (meal.isSpoiled(currentCycle, maxCycle) || currentCycle + 1 == maxCycle) {
                    totalWasteInCycle += meal.getMealType().getCost();
                    mealsToRemove.add(meal);
                }
            }
            mealsList.removeAll(mealsToRemove);
            Stack<Meal> freshStack = new Stack<>();
            freshStack.addAll(mealsList);
            newAvailableMeals.add(freshStack);
        }
        availableMeals = newAvailableMeals;
        return totalWasteInCycle;
    }

    public int getMaxMealCountByType(MealType mealType) {
        return maximumPortionsByMealType.get(mealType);
    }

    public int getMealCountByType(MealType mealType) {
        return getMealStackByType(mealType).size();
    }
}
