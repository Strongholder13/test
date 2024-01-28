package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class DepartureDateFilter {

    public static List<Flight> filterByDepartureTime (List<Flight> flights, LocalDateTime currentTime) {

        return flights.stream().filter(flight -> isBeforeCurrentTime(flight, currentTime)).collect(Collectors.toList());
    }

    private static boolean isBeforeCurrentTime(Flight flight, LocalDateTime currentTime) {
        for (Segment segment : flight.getSegments()) {
            if (currentTime.isBefore(segment.getDepartureDate())) {
                return true;
            }
        }
        return false;
    }
}
