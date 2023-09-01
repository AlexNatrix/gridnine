package com.gridnine.testing;

import java.util.List;
import java.util.stream.Collectors;

public class SegmentsFilter{  

         /**
         * Утилити класс для работы с данными полета
         */
        SegmentsFilter(){};


        /**
        * Исключает из списка полётов полёт, в котором хотя бы в одном из сегментов дата прилёта
        * раньше даты вылета.
        *
        * @param flightList - список полётов
        */    
        public List<Flight> IncorrectSegmentsFilter(List<Flight> flightList){
            return  flightList.stream().filter(
                    flight->flight.getSegments().stream().allMatch(
                    seg->!seg.getDepartureDate().isAfter(seg.getArrivalDate())
                )).collect(Collectors.toList());

        }
    }
    
