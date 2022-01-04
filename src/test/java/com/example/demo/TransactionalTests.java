package com.example.demo;

import com.example.demo.entity.Flight;
import com.example.demo.repository.FlightRepository;
import com.example.demo.services.FlightsService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionalTests {
    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightsService flightsService;

    @Before
    public void setUp(){
        flightRepository.deleteAll();
    }

    @Test
    public void shouldNotRollBackWhenTheresNoTransaction(){
        try {
            flightsService.saveFlight(new Flight());
        } catch (Exception e){
            //Do nothing
        }
        finally {
            Assertions.assertThat(flightRepository.findAll())
                    .isNotEmpty();
        }
    }

    @Test
    public void shouldNotRollBackWhenTheresIsATransaction(){
        try {
            flightsService.saveFlightTransactional(new Flight());
        } catch (Exception e){
            //Do nothing
        }
        finally {
            Assertions.assertThat(flightRepository.findAll())
                    .isEmpty();
        }
    }
}
