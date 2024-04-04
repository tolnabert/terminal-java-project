package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;
import com.codecool.ehotel.model.NameData;
import com.codecool.ehotel.model.SeasonType;
import com.codecool.ehotel.service.logger.Logger;
import com.codecool.ehotel.service.statistics.StatisticsCollector;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GuestServiceImpl implements GuestService {

    private static final Random RANDOM = new Random();
    private static final GuestType[] GUEST_TYPES = GuestType.values();
    private final String[] firstNames = NameData.FIRST_NAMES; // could be passed as parameter, as constructor
    private final String[] lastNames = NameData.LAST_NAMES; // could be passed as parameter, as constructor
    private HashMap<Integer, List<Guest>> guestsForCycles;
    private final Logger logger;
    private final StatisticsCollector statisticsCollector;

    public GuestServiceImpl(Logger logger, StatisticsCollector statisticsCollector) {
        this.logger = logger;
        this.statisticsCollector = statisticsCollector;
    }

    @Override
    public Guest generateRandomGuest(SeasonType seasonType, LocalDate seasonStart, LocalDate seasonEnd) {
        if (seasonStart.isAfter(seasonEnd)) {
            logger.logError("Season start cannot be after season end!");
            throw new IllegalArgumentException("Season start cannot be after season end.");
        }

        int firstNameIndex = RANDOM.nextInt(firstNames.length);
        int lastNameIndex = RANDOM.nextInt(lastNames.length);

        String guestRngName = firstNames[firstNameIndex] + " " + lastNames[lastNameIndex];
        GuestType guestRngType = GUEST_TYPES[RANDOM.nextInt(GUEST_TYPES.length)];
        LocalDate checkIn = generateRandomCheckIn(seasonStart, seasonEnd);
        LocalDate checkOut = generateRandomCheckOut(checkIn);

        Guest randomGuest = new Guest(guestRngName, guestRngType, checkIn, checkOut, seasonType);
        statisticsCollector.incrementNumberOfGuestOfSeason();
        return randomGuest;
    }

    @Override
    public List<Guest> generateRandomGuests(SeasonType seasonType, LocalDate seasonStart, LocalDate seasonEnd) {
        int numberOfGuests = seasonType.getNumberOfGuests();
        logger.logInfo("Generating " + numberOfGuests + " random Guests");

        List<Guest> generatedGuests = new ArrayList<>();

        for (int i = 0; i < numberOfGuests; i++) {
            generatedGuests.add(generateRandomGuest(seasonType, seasonStart, seasonEnd));
        }

        return generatedGuests;
    }

    private LocalDate generateRandomCheckIn(LocalDate seasonStart, LocalDate seasonEnd) {
        long startEpochDay = seasonStart.toEpochDay();
        long endEpochDay = seasonEnd.toEpochDay();
        long randomCheckInDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay + 1);

        return LocalDate.ofEpochDay(randomCheckInDay);
    }

    private LocalDate generateRandomCheckOut(LocalDate checkIn) {
        return checkIn.plusDays(ThreadLocalRandom.current().nextInt(1, 8));
    }

    private boolean isForToday(Guest guest, LocalDate todayDate) {
        LocalDate checkIn = guest.checkIn();
        LocalDate checkOut = guest.checkOut();

        return checkIn.isBefore(todayDate) && checkOut.isAfter(todayDate) || checkIn.isEqual(todayDate);
    }

    @Override
    public List<Guest> getGuestsForToday(List<Guest> generatedGuests, LocalDate todayDate) {
        return generatedGuests.stream()
                .filter(guest -> isForToday(guest, todayDate))
                .toList();
    }

    @Override
    public HashMap<Integer, List<Guest>> splitGuestsForDay(List<Guest> guestsForToday, int maxCycle) { //could be Map as return type, key value pair can work with
        logger.logInfo("Splitting guests for " + maxCycle + " cycles");
        guestsForCycles = new HashMap<>();

        for(int cycle = 0; cycle < maxCycle; cycle++) {
            List<Guest> temp = new ArrayList<>();
            guestsForCycles.put(cycle, temp);
        }

        for (Guest guest : guestsForToday) {
            int cycleOfGuest = RANDOM.nextInt(0, maxCycle);
            guestsForCycles.get(cycleOfGuest).add(guest);
        }
        return guestsForCycles;
    }

    @Override
    public List<Guest> getGuestsForCycle(int cycleKey) {
        List<Guest> guestsForCycle = guestsForCycles.get(cycleKey);
        logger.logInfo(guestsForCycle.size() + " guests are arriving in this cycle");
        return guestsForCycle;
    }
}
