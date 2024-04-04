package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.SeasonType;
import com.codecool.ehotel.service.guest.GuestService;
import com.codecool.ehotel.service.logger.Logger;

import java.time.LocalDate;
import java.util.List;

public class ServeBreakfastManager implements ServeManager {
    private final BuffetService buffetService;
    private final GuestService guestService;
    private final BuffetConfigurator buffetConfigurator;
    private List<Guest> seasonGuests;
    private LocalDate seasonStart;
    private LocalDate seasonEnd;
    private int maxCycle;
    private Logger logger;

    public ServeBreakfastManager(BuffetService buffetService, GuestService guestService, BuffetConfigurator buffetConfigurator, SeasonType seasonType, int maxCycle, Logger logger) {
        this.buffetService = buffetService;
        this.guestService = guestService;
        this.buffetConfigurator = buffetConfigurator;
        this.seasonStart = seasonType.getSeasonStart();
        this.seasonEnd = seasonType.getSeasonEnd();
        this.maxCycle = maxCycle;
        this.seasonGuests = generateGuests();
        this.logger = logger;
    }

    public List<Guest> generateGuests() {
        return guestService.generateRandomGuests(SeasonType.EARLY, seasonStart, seasonEnd);
    }

    @Override
    public void runSeason() {
        logger.logInfo("Starting season");
        for (LocalDate currentDate = seasonStart; currentDate.isBefore(seasonEnd); currentDate = currentDate.plusDays(1)) {
            List<Guest> todayGuests = guestService.getGuestsForToday(seasonGuests, currentDate);
            logger.logInfo("Number of today's guests: " + todayGuests.size());
            buffetService.initializeBuffet(buffetConfigurator.collectMealTypeCountsForDay(todayGuests));
            guestService.splitGuestsForDay(todayGuests, maxCycle);
            for (int i = 0; i < maxCycle; i++) {
                logger.logInfo("Starting cycle " + i);
                List<Guest> cycleGuests = guestService.getGuestsForCycle(i);
                serveMeals(cycleGuests);
            }
        }

        logger.logInfo("Season ended!");
    }

    @Override
    public void serveMeals(List<Guest> cycleGuests) {
        buffetService.runCycle(cycleGuests);
    }
}
