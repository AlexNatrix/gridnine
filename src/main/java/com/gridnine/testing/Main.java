package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.List;

// вылет до текущего момента времени
// имеются сегменты с датой прилёта раньше даты вылета
// общее время, проведённое на земле превышает два часа (время на земле — это интервал между прилётом одного сегмента и вылетом следующего за ним)


public class Main {

    public static void main(String[] args) {
        
        List<Flight> flightList = FlightBuilder.createFlights();

        System.out.println("Source");
        printFlightList(flightList);

        System.out.println("Filter before");
        printFlightList(new DepartureTimeFilter().isBefore(flightList, LocalDateTime.now()));


        System.out.println("F");
        printFlightList(new SegmentsFilter().IncorrectSegmentsFilter(flightList));

        System.out.println("List of flights, excluding flights with a total waiting time of more than 2 hours: ");
        printFlightList(new TransferTimeFilter().MoreThan(flightList,120));

    }

    /**
     * Выводит в консоль список полётов в удобочитаемом формате.
     */
    private static void printFlightList(List<Flight> flightList){
        int i = 1;
        for(Flight flight : flightList){
            System.out.println(i + ". " + flight);
            i++;
        }
        System.out.println("\n");
    }
}
