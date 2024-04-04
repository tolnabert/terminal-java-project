package com.codecool.ehotel.model;

import java.time.LocalDate;

public enum SeasonType {
    EARLY(1200, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31)),
    MID(1500, LocalDate.of(2024, 2, 1), LocalDate.of(2024, 2, 29)),
    LATE(1400, LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 31));

    private final int numberOfGuests;
    private final LocalDate seasonStart;
    private final LocalDate seasonEnd;

    SeasonType(int numberOfGuests, LocalDate seasonStart, LocalDate seasonEnd) {

        this.numberOfGuests = numberOfGuests;
        this.seasonStart = seasonStart;
        this.seasonEnd = seasonEnd;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public LocalDate getSeasonStart() {
        return seasonStart;
    }

    public LocalDate getSeasonEnd() {
        return seasonEnd;
    }
}
