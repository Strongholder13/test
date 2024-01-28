package com.gridnine.testing;


public class SegmentOrderFilter {

    static boolean isValidOrder(Flight flight) {
        for (Segment segment : flight.getSegments()) {
            if (segment.getArrivalDate().isBefore(segment.getDepartureDate())) {
                return false;
            }
        }
        return true;
    }


}
