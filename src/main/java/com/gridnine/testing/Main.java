package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Flight> flightList = FlightBuilder.createFlights();

        System.out.println("Тестовый список полётов без фильтрации:");
        printFlightList(flightList);

        System.out.println("Список полётов, исключающий полёты с вылетом до текущего момента времени:");
        printFlightList(FlightFilter.excludeByDeparture(flightList, LocalDateTime.now(), ComparisonNumber.LESS));

        System.out.println("Список полётов, исключающий полёты, " +
                "имеющие хотя бы один сегмент с датой прилета раньше даты вылета: ");
        printFlightList(FlightFilter.excludeIncorrectSegments(flightList));

        System.out.println("Список полётов, исключающий полёты с общим временем ожидания более 2-х часов: ");
        printFlightList(FlightFilter.excludeSumInterval(flightList, 120, ComparisonNumber.MORE));

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
