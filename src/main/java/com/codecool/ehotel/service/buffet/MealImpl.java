package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.MealDurability;
import com.codecool.ehotel.model.MealType;

import java.time.LocalDate;

public class MealImpl implements Meal {
    private int cycleCreated;
    private MealType mealType;

    public MealImpl(int cycleCreated, MealType mealType) {//abstract meal and subclasses for short - medium -long
        this.cycleCreated = cycleCreated;
        this.mealType = mealType;
    }

    public boolean isSpoiled(int currentCycle, int maxCycle) {
        int shortMealDurabilityInCycles = 2;
        if (mealType.getDurability() == MealDurability.SHORT) {
            return cycleCreated + shortMealDurabilityInCycles == currentCycle;
        } else if (mealType.getDurability() == MealDurability.MEDIUM) {
            return currentCycle + 1 == maxCycle;
        } else if (mealType.getDurability() == MealDurability.LONG) {
            return false;
        }
        return false;
    }

    @Override
    public MealType getMealType() {
        return mealType;
    }

    @Override
    public String toString() {
        return mealType + " created in cycle " + cycleCreated;
    }
}

