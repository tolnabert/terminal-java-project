package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;
import com.codecool.ehotel.model.MealType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MealOptimizationTest {


    @Test
    public void CollectMealTypeCountsWithOneMuffinAndOtherIsZero() {
        Guest newGuest = new Guest(
                "Bob Test",
                GuestType.TOURIST,
                LocalDate.of(2024, 1, 20),
                LocalDate.of(2024, 1, 25));

        MealOptimization mealOptimization = new MealOptimization();
        HashMap<MealType, Integer> buffetMealTypesWithQty = mealOptimization.collectMealTypeCountsForDay(List.of(newGuest));

        int muffinQty = buffetMealTypesWithQty.get(MealType.MUFFIN);

        assertEquals(1, muffinQty);
    }

    @Test
    public void CollectMealTypeCountsWithThreeMuffinsAndFourGuestsShouldBeThree() {
        MealOptimization mealOptimization = new MealOptimization();

        List<Guest> collectGuests = getGuests();
        HashMap<MealType, Integer> buffetMealTypesWithQty = mealOptimization.collectMealTypeCountsForDay(collectGuests);

        int muffinQty = buffetMealTypesWithQty.get(MealType.MUFFIN);

        assertEquals(1, muffinQty);
    }

    @Test
    public void CollectMealTypeCountsWithOneScrambledEggAndFourGuestsShouldBeOne() {
        MealOptimization mealOptimization = new MealOptimization();

        List<Guest> collectGuests = getGuests();
        HashMap<MealType, Integer> buffetMealTypesWithQty = mealOptimization.collectMealTypeCountsForDay(collectGuests);

        int scrambledEggQty = buffetMealTypesWithQty.get(MealType.SCRAMBLED_EGGS);

        assertEquals(1, scrambledEggQty);
    }

    @Test
    public void CollectMealTypeCountsWithOneMashedPotatoesAndFourGuestsShouldBeOne() {
        MealOptimization mealOptimization = new MealOptimization();

        List<Guest> collectGuests = getGuests();
        HashMap<MealType, Integer> buffetMealTypesWithQty = mealOptimization.collectMealTypeCountsForDay(collectGuests);

        int mashedPotatoQty = buffetMealTypesWithQty.get(MealType.MASHED_POTATO);

        assertEquals(1, mashedPotatoQty);
    }

    private static List<Guest> getGuests() {
        Guest guestOne = new Guest(
                "Bob Test",
                GuestType.TOURIST,
                LocalDate.of(2024, 1, 20),
                LocalDate.of(2024, 1, 25));

        Guest guestTwo = new Guest(
                "Alice Example",
                GuestType.BUSINESS,
                LocalDate.of(2024, 2, 5),
                LocalDate.of(2024, 2, 10));

        Guest guestThree = new Guest(
                "Charlie Visitor",
                GuestType.TOURIST,
                LocalDate.of(2024, 3, 15),
                LocalDate.of(2024, 3, 20));


        Guest guestFour = new Guest(
                "David Tester",
                GuestType.KID,
                LocalDate.of(2024, 4, 10),
                LocalDate.of(2024, 4, 15));

        List<Guest> collectGuests = List.of(guestOne, guestTwo, guestThree, guestFour);
        return collectGuests;
    }
}