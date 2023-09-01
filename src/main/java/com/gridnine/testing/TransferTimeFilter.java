package com.gridnine.testing;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;





public class TransferTimeFilter{


        TransferTimeFilter(){}


        /**
         *
         * Сравнивает время пересадки с заданным интервалом, и возвращает отфильтрованный список значений в котором 
         * для всех значений полетов время трансфера больше заданного интервала
         * 
         * @param flightList - список полётов
         * @param minutes - интервал в минутах, который необходим для сравнения
         *
         */

        public List<Flight> LessThan(List<Flight> flightList,int minutes){
            List<Flight> filteredFlights = new ArrayList<>();

            for (Flight flight: flightList) {
                List<Segment> segments = flight.getSegments();
                int tempInterval = 0;

                if (segments.size() > 1){

                    for (int i = 0; i < segments.size() - 1; i++) {
                        tempInterval += segments.get(i).getArrivalDate().until(segments.get(i + 1).getDepartureDate(),
                                ChronoUnit.MINUTES);
                    }
                    if (!(tempInterval<minutes)) {
                        filteredFlights.add(flight);
                    }
                }else{
                    filteredFlights.add(flight);
                }
                
            }

            return  filteredFlights;
        }

        
        /**
         *
         * Сравнивает время пересадки с заданным интервалом, и возвращает отфильтрованный список значений в котором 
         * для всех значений полетов время трансфера меньше заданного интервала
         * 
         * @param flightList - список полётов
         * @param minutes - интервал в минутах, который необходим для сравнения
         *
         */
        public List<Flight> MoreThan(List<Flight> flightList,int minutes){
            List<Flight> filteredFlights = new ArrayList<>();

            for (Flight flight: flightList) {
                List<Segment> segments = flight.getSegments();
                int tempInterval = 0;

                if (segments.size() > 1){

                    for (int i = 0; i < segments.size() - 1; i++) {
                        tempInterval += segments.get(i).getArrivalDate().until(segments.get(i + 1).getDepartureDate(),
                                ChronoUnit.MINUTES);
                    }
                    if (!(tempInterval>minutes)) {
                        filteredFlights.add(flight);
                    }
                }else{
                    filteredFlights.add(flight);
                }     
            }
            return  filteredFlights;

        }



        /**
         *
         * Сравнивает время пересадки с заданным интервалом, и возвращает отфильтрованный список значений в котором 
         * для всех значений полетов время трансфера НЕ равно заданному интервалу.
         * 
         * @param flightList - список полётов
         * @param minutes - интервал в минутах, который необходим для сравнения
         *
         */


        public List<Flight>  Equal(List<Flight> flightList,int minutes){
            List<Flight> filteredFlights = new ArrayList<>();

            for (Flight flight: flightList) {
                List<Segment> segments = flight.getSegments();
                int tempInterval = 0;

                if (segments.size() > 1){

                    for (int i = 0; i < segments.size() - 1; i++) {
                        tempInterval += segments.get(i).getArrivalDate().until(segments.get(i + 1).getDepartureDate(),
                                ChronoUnit.MINUTES);
                    }
                    if (tempInterval!=minutes) {
                        filteredFlights.add(flight);
                    }
                }else{
                    filteredFlights.add(flight);
                }     
            }
            return  filteredFlights;
        }
    }