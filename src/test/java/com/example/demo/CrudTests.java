package com.example.demo;


import com.example.demo.entity.Flight;
import com.example.demo.repository.FlightRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ObjectAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CrudTests {

    @Autowired
    private FlightRepository flightRepository;

    @Test
    public void shouldPerformCRUDOperations(){
        final Flight flight = new Flight();
        flight.setOrigin("Amsterdam");
        flight.setDestination("New York");
        flight.setScheduledAt(LocalDateTime.parse("2022-01-10T12:12:00"));

        flightRepository.save(flight);

        Assertions.assertThat(flightRepository.findAll())
                .hasSize(1)
                .first()
                .isEqualTo(flight);

        flightRepository.deleteById(flight.getId());

        Assertions.assertThat(flightRepository.count()).isZero();

    }
}
