package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.List;


public class Main {
    public static void main(String[] args) {



        List<Flight> fligtsList = FlightBuilder.createFlights();

        List<Flight> filtrtedCurentDate = DepartureDateFilter.filterByDepartureTime(fligtsList, LocalDateTime.now());
        List<Flight> filtrtedSegmentDate = fligtsList.stream().filter(SegmentOrderFilter::isValidOrder).toList();
        List<Flight> filtrtedDuration = DurationSegmentFilter.filterByDuration(fligtsList, 2);

        

        filtrtedCurentDate.forEach(System.out::println);
        System.out.println("___________________");
        filtrtedSegmentDate.forEach(System.out::println);
        System.out.println("___________________");
        filtrtedDuration.forEach(System.out::println);


    }
}