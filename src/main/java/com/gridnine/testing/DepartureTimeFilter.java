package com.gridnine.testing;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;





public class DepartureTimeFilter {
            
        /**
         * Класс для работы с временем отправления
         */
        DepartureTimeFilter(){};
        

        private static void formatDate(LocalDateTime dateTime){
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            dateTime.format(fmt);
        }

        
    
        /**
         * Сравнивает дату вылета каждого полета из списка с конкретной датой и, в зависимости от
         * параметров, возвращает обновленный список полетов.
         *
         * @param flightList - список полётов
         * @param requiredDate - дата, с которой сравнивается дата вылета
         */
        public List<Flight> isBefore(List<Flight> flightList,LocalDateTime requiredDate){
            formatDate(requiredDate);
            return flightList.stream().filter(flight ->!flight.getSegments().get(0).getDepartureDate().isBefore(requiredDate)).collect(Collectors.toList());
        }


        /**
         * Сравнивает дату вылета каждого полета из списка с конкретной датой и, в зависимости от
         * параметров, возвращает обновленный список полетов.
         *
         * @param flightList - список полётов
         * @param requiredDate - дата, с которой сравнивается дата вылета
         */

        public List<Flight> isAfter(List<Flight> flightList,LocalDateTime requiredDate){
            formatDate(requiredDate);
            return flightList.stream().filter(flight ->!flight.getSegments().get(0).getDepartureDate().isAfter(requiredDate)).collect(Collectors.toList());
        }


        /**
         * Сравнивает дату вылета каждого полета из списка с конкретной датой и, в зависимости от
         * параметров, возвращает обновленный список полетов.
         *
         * @param flightList - список полётов
         * @param requiredDate - дата, с которой сравнивается дата вылета
         */

        public List<Flight> isEqual(List<Flight> flightList,LocalDateTime requiredDate){
            formatDate(requiredDate);
            return flightList.stream().filter(flight ->!flight.getSegments().get(0).getDepartureDate().isEqual(requiredDate)).collect(Collectors.toList());
        }
     
    }
