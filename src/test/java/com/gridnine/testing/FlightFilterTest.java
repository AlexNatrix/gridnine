package com.gridnine.testing;

import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlightFilterTest {

    final LocalDateTime dateTime = LocalDateTime.parse("2021-01-01T00:00");

    @Test
    public void filterByDeparture(){

        List<Flight> flights = Arrays.asList(
                // #0 A normal flight
                createFlight(dateTime, dateTime.plusHours(2)),
                // #1 A flight with a departure date in the past
                createFlight(dateTime.minusMinutes(1), dateTime.plusHours(2)),
                // #2 A flight with a future departure date
                createFlight(dateTime.plusMinutes(1), dateTime.plusHours(2)),
                // #3 A normal multi segment flight
                createFlight(dateTime, dateTime.plusHours(2),
                        dateTime.plusHours(3), dateTime.plusHours(4),
                        dateTime.plusHours(6), dateTime.plusHours(7)),
                // #4 A multi segment flight with a departure date in the past
                createFlight(dateTime.minusMinutes(1), dateTime.plusHours(2),
                        dateTime.plusHours(3), dateTime.plusHours(4),
                        dateTime.plusHours(6), dateTime.plusHours(7)),
                // #5 A multi segment flight with a future departure date
                createFlight(dateTime.plusMinutes(1), dateTime.plusHours(2),
                        dateTime.plusHours(3), dateTime.plusHours(4),
                        dateTime.plusHours(6), dateTime.plusHours(7)));



   
        List<Flight> moreCaseActual = new DepartureTimeFilter().isAfter(flights, dateTime);
        List<Flight> lessCaseActual = new DepartureTimeFilter().isBefore(flights, dateTime);
        List<Flight> equallyCaseActual = new DepartureTimeFilter().isEqual(flights, dateTime);

        List<Flight> moreCaseExpected = makeReferenceArray(flights, new int[] { 0, 1, 3, 4 });
        List<Flight> lessCaseExpected = makeReferenceArray(flights, new int[] { 0, 2, 3, 5 });
        List<Flight> equallyCaseExpected = makeReferenceArray(flights, new int[] { 1, 2, 4, 5 });


        assertEquals(lessCaseExpected, lessCaseActual);
        assertEquals(moreCaseExpected, moreCaseActual);
        assertEquals(equallyCaseExpected, equallyCaseActual);
    }

    @Test
    public void filterIncorrectSegments(){

        List<Flight> flights = Arrays.asList(
                // #0 A normal flight
                createFlight(dateTime, dateTime.plusHours(2)),
                // #1 A incorrect flight
                createFlight(dateTime, dateTime.minusHours(2)),
                // #2 A normal multi segment flight
                createFlight(dateTime, dateTime.plusHours(2),
                        dateTime.plusHours(3), dateTime.plusHours(4),
                        dateTime.plusHours(6), dateTime.plusHours(7)),
                // #3 A multi segment flight with incorrect first segment
                createFlight(dateTime, dateTime.minusHours(2),
                        dateTime.plusHours(3), dateTime.plusHours(4),
                        dateTime.plusHours(6), dateTime.plusHours(7)),
                // #4 A multi segment flight with incorrect middle segment
                createFlight(dateTime, dateTime.plusHours(2),
                        dateTime.plusHours(3), dateTime.plusHours(1),
                        dateTime.plusHours(6), dateTime.plusHours(7)),
                // #5 A multi segment flight with incorrect last segment
                createFlight(dateTime, dateTime.plusHours(2),
                        dateTime.plusHours(3), dateTime.plusHours(4),
                        dateTime.plusHours(6), dateTime.plusHours(5)));

        List<Flight> correctFlightsActual = new SegmentsFilter().IncorrectSegmentsFilter(flights);
        List<Flight> correctFlightsExpected = makeReferenceArray(flights, new int[] { 0, 2});

        assertEquals(correctFlightsExpected, correctFlightsActual);
    }

    @Test
    public void filterSumInterval(){

        List<Flight> flights = Arrays.asList(
                // #0 A normal flight
                createFlight(dateTime, dateTime.plusHours(2)),
                // #1 A multi segment flight with summary ground interval less than 120 minutes
                createFlight(dateTime, dateTime.plusHours(2),
                        dateTime.plusHours(3), dateTime.plusHours(4),
                        dateTime.plusHours(4).plusMinutes(30), dateTime.plusHours(7)),
                // #2 A multi segment flight with summary ground interval equally 120 minutes
                createFlight(dateTime, dateTime.plusHours(2),
                        dateTime.plusHours(3), dateTime.plusHours(4),
                        dateTime.plusHours(5), dateTime.plusHours(7)),
                // #3  multi segment flight with summary ground interval more than 120 minutes
                createFlight(dateTime, dateTime.plusHours(2),
                        dateTime.plusHours(3), dateTime.plusHours(4),
                        dateTime.plusHours(6), dateTime.plusHours(7)),
                // #4  multi segment flight with 120 minutes ground interval between 1st and 2nd segments
                createFlight(dateTime, dateTime.plusHours(2),
                        dateTime.plusHours(4), dateTime.plusHours(5),
                        dateTime.plusHours(5), dateTime.plusHours(7)),
                // #5  multi segment flight with 120 minutes ground interval between 2nd and 3rd segments
                createFlight(dateTime, dateTime.plusHours(2),
                        dateTime.plusHours(2), dateTime.plusHours(3),
                        dateTime.plusHours(5), dateTime.plusHours(7)),
                // #6  multi segment flight with ground interval more than 120 minutes between 1st and 2nd segments
                createFlight(dateTime, dateTime.plusHours(1),
                        dateTime.plusHours(4), dateTime.plusHours(5),
                        dateTime.plusHours(5), dateTime.plusHours(7)),
                // #7  multi segment flight with ground interval more than 120 minutes between 2nd and 3rd segments
                createFlight(dateTime, dateTime.plusHours(2),
                        dateTime.plusHours(2), dateTime.plusHours(3),
                        dateTime.plusHours(6), dateTime.plusHours(7)),
                // #8  multi segment flight with ground interval less than 120 minutes between 1st and 2nd segments
                createFlight(dateTime, dateTime.plusHours(1),
                        dateTime.plusHours(2), dateTime.plusHours(5),
                        dateTime.plusHours(5), dateTime.plusHours(7)),
                // #9  multi segment flight with ground interval less than 120 minutes between 2nd and 3rd segments
                createFlight(dateTime, dateTime.plusHours(2),
                        dateTime.plusHours(2), dateTime.plusHours(3),
                        dateTime.plusHours(4), dateTime.plusHours(7)));



        List<Flight> moreCaseActual = new TransferTimeFilter().MoreThan(flights, 120);
        List<Flight> lessCaseActual = new TransferTimeFilter().LessThan(flights, 120);
        List<Flight> equallyCaseActual = new TransferTimeFilter().Equal(flights, 120);

        List<Flight> moreCaseExpected = makeReferenceArray(flights, new int[] { 0, 1, 2, 4, 5, 8, 9});
        List<Flight> lessCaseExpected = makeReferenceArray(flights, new int[] { 0, 2, 3, 4, 5, 6, 7});
        List<Flight> equallyCaseExpected = makeReferenceArray(flights, new int[] { 0, 1, 3, 6, 7, 8, 9});

        assertEquals(moreCaseExpected, moreCaseActual);
        assertEquals(lessCaseExpected, lessCaseActual);
        assertEquals(equallyCaseExpected, equallyCaseActual);
    }

    /**
     * Создает экземпляр класса Flight.
     */
    private Flight createFlight(final LocalDateTime... dates) {

        if ((dates.length % 2) != 0) {
            throw new IllegalArgumentException(
                    "you must pass an even number of dates");
        }
        List<Segment> segments = new ArrayList<>(dates.length / 2);
        for (int i = 0; i < (dates.length - 1); i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }
        return new Flight(segments);
    }

    /**
     * Создает список полётов по заданным параметрам
     *
     * @param flights - исходный список полётов
     * @param indices - массив с индексами элементов, которые необходимо взять из списка flightsю
     */
    private List<Flight> makeReferenceArray(List<Flight> flights, int[] indices){
        List<Flight> referenceArr = new ArrayList<>();
        for (int index: indices){
            referenceArr.add(flights.get(index));
        }
        return referenceArr;
    }
}
