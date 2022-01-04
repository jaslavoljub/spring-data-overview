package com.example.demo;

import com.example.demo.entity.Flight;
import com.example.demo.repository.FlightRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DerivedQueryTests {

    @Autowired
    private FlightRepository flightRepository;

    @Before
    public void setUp(){
        flightRepository.deleteAll();
    }

    @Test
    public void shouldFindFlightsFromLondon(){
        final Flight flight1 = createFlight("London");
        final Flight flight2 = createFlight("London");
        final Flight flight3 = createFlight("New York");

        flightRepository.save(flight1);
        flightRepository.save(flight2);
        flightRepository.save(flight3);

        List<Flight> flightsToLondon = flightRepository.findByOrigin("London");

        Assertions.assertThat(flightsToLondon).hasSize(2);
        Assertions.assertThat(flightsToLondon.get(0)).isEqualTo(flight1);
        Assertions.assertThat(flightsToLondon.get(1)).isEqualTo(flight2);


    }

    @Test
    public void shouldFindFlightsFromLondonToParis() {
        final Flight flight1 = createFlight("London", "Paris");
        final Flight flight2 = createFlight("London", "New York");
        final Flight flight3 = createFlight("New York","Paris");

        flightRepository.save(flight1);
        flightRepository.save(flight2);
        flightRepository.save(flight3);

        List<Flight> flightsLondonToParis =
                flightRepository.findByOriginAndDestination("London","Paris");

        Assertions.assertThat(flightsLondonToParis)
                .hasSize(1)
                .first()
                .isEqualTo(flight1);
    }

    @Test
    public void shouldFindFlightsFromLondonOrMadrid() {
        final Flight flight1 = createFlight("London", "Paris");
        final Flight flight2 = createFlight("Madrid", "New York");
        final Flight flight3 = createFlight("New York", "Paris");

        flightRepository.save(flight1);
        flightRepository.save(flight2);
        flightRepository.save(flight3);

        List<Flight> flightsLondonOrMadrid =
                flightRepository.findByOriginIn("London", "Madrid");

        Assertions.assertThat(flightsLondonOrMadrid).hasSize(2);
        Assertions.assertThat(flightsLondonOrMadrid.get(0)).isEqualTo(flight1);
        Assertions.assertThat(flightsLondonOrMadrid.get(1)).isEqualTo(flight2);
    }


    private Flight createFlight(String origin, String destination){
        final Flight flight= new Flight();
        flight.setOrigin(origin);
        flight.setDestination(destination);
        flight.setScheduledAt(LocalDateTime.parse("2022-01-20T12:00:00"));
        return flight;
    }

    private Flight createFlight(String origin){
        final Flight flight= new Flight();
        flight.setOrigin(origin);
        flight.setDestination("Madrid");
        flight.setScheduledAt(LocalDateTime.parse("2022-01-15T12:00:00"));
        return flight;
    }
}
