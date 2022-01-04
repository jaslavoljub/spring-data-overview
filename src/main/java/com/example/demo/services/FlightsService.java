package com.example.demo.services;

import com.example.demo.entity.Flight;
import com.example.demo.repository.FlightRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class FlightsService {
    private final FlightRepository flightRepository;


    public FlightsService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public void saveFlight(Flight flight){
        flightRepository.save(flight);
        throw new RuntimeException("I failde");
    }

    @Transactional
    public void saveFlightTransactional(Flight flight) {
        flightRepository.save(flight);
        throw new RuntimeException("I failde");
    }
}
