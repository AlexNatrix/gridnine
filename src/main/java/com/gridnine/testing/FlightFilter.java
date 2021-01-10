package com.gridnine.testing;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class FlightFilter {

    /**
     * <p>Сравнивает дату вылета каждого полета из списка с конкретной датой и, в зависимости от
     * параметров, возвращает обновленный список полетов.</p>
     *
     * @param flightList - список полётов
     * @param requiredDate - дата, с которой сравнивается дата вылета
     * @param comparisonNumber - знак сравнения, который указывает по какому правилу сравнивать
     *                         дату вылета каждого полёта с requiredDate
     *                         (A # B -> где A - дата вылета, B - requiredDate, а # - comparisonNumber)
     */
    public static List<Flight> excludeByDeparture(List<Flight> flightList, LocalDateTime requiredDate,
                                           ComparisonNumber comparisonNumber) {

        List<Flight> filteredFlights = new ArrayList<>();

        for (Flight flight: flightList) {
            List <Segment> segments = flight.getSegments();
            LocalDateTime departureDate = segments.get(0).getDepartureDate();

            formatDate(requiredDate);

            switch (comparisonNumber){
                case EQUALLY:
                    if (!departureDate.isEqual(requiredDate)) {
                        filteredFlights.add(flight);
                    } break;

                case LESS:
                    if (!departureDate.isBefore(requiredDate)) {
                        filteredFlights.add(flight);
                    } break;

                case MORE:
                    if (!departureDate.isAfter(requiredDate)) {
                        filteredFlights.add(flight);
                    } break;
            }
        }

        return filteredFlights;
    }


    /**
     * <p>Исключает из списка полётов полёт, в котором хотя бы в одном из сегментов дата прилёта
     * раньше даты вылета.</p>
     *
     * @param flightList - список полётов
     */
    public static List<Flight> excludeIncorrectSegments(List<Flight> flightList){

        List<Flight> filteredFlights = new ArrayList<>();

        for (Flight flight: flightList) {

            List <Segment> segments = flight.getSegments();
            boolean excludeFlag = false;

            for (Segment segment: segments){
                if (segment.getDepartureDate().isAfter(segment.getArrivalDate())){
                    excludeFlag = true;
                    break;
                }
            }

            if (!excludeFlag) {
                filteredFlights.add(flight);
            }
        }

        return filteredFlights;
    }

    /**
     * <p>Формирует новый список полётов, основвываясь на суммарном интервале нахождения пассажира на земле
     * в ожном полете и заданных параметрами <i>minutes</i> и <i>comparisonNumber</i> условиях.</p>
     *
     * @param flightList - список полётов
     * @param minutes - интервал в минутах, который необходим для сравнения
     * @param comparisonNumber - знак сравнения, который указывает по какому правилу сравнивать
     *                         суммарное время пребывания на земле конкретного полета с требуемым
     *                         (A # B -> где A - суммарный интервал на земле, B - minutes, а # - comparisonNumber)
     *
     */

    public static List<Flight> excludeSumInterval(List<Flight> flightList, int minutes, ComparisonNumber comparisonNumber){

        List<Flight> filteredFlights = new ArrayList<>();

        for (Flight flight: flightList) {
            List<Segment> segments = flight.getSegments();
            int tempInterval = 0;

            if (segments.size() > 1){

                for (int i = 0; i < segments.size() - 1; i++) {
                    tempInterval += segments.get(i).getArrivalDate().until(segments.get(i + 1).getDepartureDate(),
                            ChronoUnit.MINUTES);
                }

                switch (comparisonNumber) {
                    case EQUALLY:
                        if (!(tempInterval == minutes)) {
                            filteredFlights.add(flight);
                        }
                        break;
                    case MORE:
                        if (!(tempInterval > minutes)) {
                            filteredFlights.add(flight);
                        }
                        break;

                    case LESS:
                        if (!(tempInterval < minutes)) {
                            filteredFlights.add(flight);
                        }
                        break;
                }

            } else {
                filteredFlights.add(flight);
            }
        }

        return filteredFlights;
    }

    private static void formatDate(LocalDateTime dateTime){
        DateTimeFormatter fmt =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        dateTime.format(fmt);
    }

}
