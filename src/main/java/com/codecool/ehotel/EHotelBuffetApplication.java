package com.codecool.ehotel;

import com.codecool.ehotel.model.SeasonType;
import com.codecool.ehotel.service.buffet.*;
import com.codecool.ehotel.service.guest.GuestService;
import com.codecool.ehotel.service.guest.GuestServiceImpl;
import com.codecool.ehotel.service.logger.ConsoleLogger;
import com.codecool.ehotel.service.logger.Logger;
import com.codecool.ehotel.service.statistics.StatisticsCollector;
import com.codecool.ehotel.service.statistics.StatisticsCollectorImpl;

public class EHotelBuffetApplication {

    public static void main(String[] args) {

        final int cycleForDay = 8;

        Logger logger = new ConsoleLogger();
        StatisticsCollector statisticsCollector = new StatisticsCollectorImpl();
        GuestService guestService = new GuestServiceImpl(logger, statisticsCollector);
        BuffetConfigurator mealOptimization = new MealOptimization(logger);
        BuffetService buffetService = new BuffetServiceImpl(cycleForDay, logger, statisticsCollector);

        ServeManager serveManager = new ServeBreakfastManager(
                buffetService,
                guestService,
                mealOptimization,
                SeasonType.EARLY,
                cycleForDay,
                logger
        );

        serveManager.runSeason();

        logger.logDebug(statisticsCollector.toString());
    }
}
