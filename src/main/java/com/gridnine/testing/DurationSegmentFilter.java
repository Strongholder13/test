package com.gridnine.testing;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DurationSegmentFilter {

    public static List<Flight> filterByDuration(List<Flight> flights, int interval) {

        return flights.stream().filter(flight -> isValidDuration(flight, interval)).collect(Collectors.toList());
    }

    private static boolean isValidDuration(Flight flight, int interval) {
        List<Segment> segments = flight.getSegments();
        for (int i = 1; i < segments.size(); i++) {
            if (Duration.between(segments.get(i - 1).getArrivalDate(), segments.get(i).getDepartureDate()).toHours() > interval) {
                return false;
            }
        }
        return true;
    }


}
