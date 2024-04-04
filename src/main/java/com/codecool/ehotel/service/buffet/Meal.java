package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.MealType;

import java.time.LocalDate;

public interface Meal {

    public boolean isSpoiled(int currentCycle, int maxCycle);//to add CycleTracker cycleTracker

    public MealType getMealType();
}
