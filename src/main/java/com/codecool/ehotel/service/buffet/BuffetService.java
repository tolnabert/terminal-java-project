package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.MealType;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface BuffetService {

    public Buffet refill();

    public boolean consumeFreshest(MealType mealType);

    public void calculateWaste();

    public void initializeBuffet(LinkedHashMap<MealType, Integer> maximumPortionsByMealType);

    public void runCycle(List<Guest> guestForCurrentCycle);

}
