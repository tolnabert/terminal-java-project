package com.codecool.ehotel.model;

import com.codecool.ehotel.service.buffet.Meal;
import com.codecool.ehotel.service.buffet.MealImpl;
import com.codecool.ehotel.service.statistics.StatisticsCollector;
import com.codecool.ehotel.service.statistics.StatisticsCollectorImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuffetTest {

    private LinkedHashMap<MealType, Integer> maximumPortionByMealType;
    private StatisticsCollector statisticsCollector;
    private Buffet buffet;
    @BeforeEach
    void initialize() {
        statisticsCollector = new StatisticsCollectorImpl();
        this.maximumPortionByMealType = new LinkedHashMap<>();
        maximumPortionByMealType.put(MealType.SCRAMBLED_EGGS, 2);
        maximumPortionByMealType.put(MealType.SUNNY_SIDE_UP, 2);
        maximumPortionByMealType.put(MealType.MASHED_POTATO, 2);
        this.buffet = new Buffet(statisticsCollector);
    }


    @Test
    public void calculateWaste() {
        buffet.refill(0);
        int waste = buffet.calculateWaste(2, 8);
        assertEquals(280, waste);
        System.out.println(waste);
    }

    @Test
    public void calculateZeroWaste() {
        buffet.refill(1);
        int waste = buffet.calculateWaste(2, 8);
        assertEquals(0, waste);
        System.out.println(waste);
    }

    @Test
    public void Test_isSpoiledStillFreshShort() {
        Meal meal = new MealImpl(0, MealType.SCRAMBLED_EGGS);
        assertFalse(meal.isSpoiled(1, 8));
    }

    @Test
    public void Test_isSpoiledStillFreshMedium() {
        Meal meal = new MealImpl(0, MealType.MUFFIN);
        assertFalse(meal.isSpoiled(1, 8));
    }

    @Test
    public void Test_isSpoiledStillFreshLong() {
        Meal meal = new MealImpl(0, MealType.MILK);
        assertFalse(meal.isSpoiled(1, 8));
    }

    @Test
    public void Test_isSpoiledWastedShort() {
        Meal meal = new MealImpl(0, MealType.SCRAMBLED_EGGS);
        assertTrue(meal.isSpoiled(2, 8));
    }

    @Test
    public void Test_isSpoiledWastedMedium() {
        Meal meal = new MealImpl(0, MealType.MUFFIN);
        assertTrue(meal.isSpoiled(8, 8));
    }

}